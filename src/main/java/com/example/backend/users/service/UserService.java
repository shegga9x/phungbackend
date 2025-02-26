package com.example.backend.users.service;

import com.example.backend.auth.SecurityUtil;
import com.example.backend.s3.UploadedFile;
import com.example.backend.s3.repository.UploadedFileRepository;
import com.example.backend.s3.service.FileUploadService;
import com.example.backend.telosys.rest.dto.UserDTO;
import com.example.backend.telosys.rest.services.commons.GenericService;
import com.example.backend.users.PasswordResetToken;
import com.example.backend.users.User;
import com.example.backend.users.VerificationCode;
import com.example.backend.users.data.CreateUserRequest;
import com.example.backend.users.data.UpdateUserPasswordRequest;
import com.example.backend.users.data.UpdateUserRequest;
import com.example.backend.users.data.UserResponse;
import com.example.backend.users.jobs.SendResetPasswordEmailJob;
import com.example.backend.users.jobs.SendWelcomeEmailJob;
import com.example.backend.users.repository.PasswordResetTokenRepository;
import com.example.backend.users.repository.UserRepository;
import com.example.backend.users.repository.VerificationCodeRepository;
import com.example.backend.util.exception.ApiException;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.jobrunr.scheduling.BackgroundJobRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService extends GenericService<User, UserDTO> {
  private static final Logger logger = LoggerFactory.getLogger(UserService.class);
  private final UserRepository userRepository;
  private final VerificationCodeRepository verificationCodeRepository;
  private final PasswordResetTokenRepository passwordResetTokenRepository;
  private final UploadedFileRepository uploadedFileRepository;
  private final PasswordEncoder passwordEncoder;
  private final FileUploadService fileUploadService;

  public UserService(UserRepository userRepository,
      VerificationCodeRepository verificationCodeRepository,
      PasswordResetTokenRepository passwordResetTokenRepository,
      UploadedFileRepository uploadedFileRepository,
      PasswordEncoder passwordEncoder,
      FileUploadService fileUploadService) {
    super(User.class, UserDTO.class);
    this.userRepository = userRepository;
    this.verificationCodeRepository = verificationCodeRepository;
    this.passwordResetTokenRepository = passwordResetTokenRepository;
    this.uploadedFileRepository = uploadedFileRepository;
    this.passwordEncoder = passwordEncoder;
    this.fileUploadService = fileUploadService;
  }

  @Transactional
  public UserResponse create(@Valid CreateUserRequest request) {
    User user = new User(request);
    user = userRepository.save(user);
    sendVerificationEmail(user);
    return new UserResponse(user);
  }

  private void sendVerificationEmail(User user) {
    VerificationCode verificationCode = new VerificationCode(user);
    user.setVerificationCode(verificationCode);
    verificationCodeRepository.save(verificationCode);
    SendWelcomeEmailJob sendWelcomEmailJob = new SendWelcomeEmailJob(user.getId());
    BackgroundJobRequest.enqueue(sendWelcomEmailJob);
  }

  @Transactional
  public void verifyEmail(String code) {
    VerificationCode verificationCode = verificationCodeRepository.findByCode(code)
        .orElseThrow(() -> ApiException.builder().status(400).message("Invalid token").build());
    User user = verificationCode.getUser();
    user.setVerified(true);
    userRepository.save(user);
    verificationCodeRepository.delete(verificationCode);
  }

  @Transactional
  public void forgotPassword(String email) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> ApiException.builder().status(404).message("User not found").build());
    PasswordResetToken passwordResetToken = new PasswordResetToken(user);
    passwordResetTokenRepository.save(passwordResetToken);
    SendResetPasswordEmailJob sendResetPasswordEmailJob = new SendResetPasswordEmailJob(passwordResetToken.getId());
    BackgroundJobRequest.enqueue(sendResetPasswordEmailJob);
  }

  @Transactional
  public void resetPassword(UpdateUserPasswordRequest request) {
    PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(request.getPasswordResetToken())
        .orElseThrow(() -> ApiException.builder().status(404).message("Password reset token not found").build());

    if (passwordResetToken.isExpired()) {
      throw ApiException.builder().status(400).message("Password reset token is expired").build();
    }

    User user = passwordResetToken.getUser();
    user.updatePassword(request.getPassword());
    userRepository.save(user);
  }

  @Transactional
  public UserResponse update(UpdateUserRequest request) {
    User user = SecurityUtil.getAuthenticatedUser();
    user = userRepository.getReferenceById(user.getId());
    user.update(request);
    user = userRepository.save(user);
    return new UserResponse(user);
  }

  @Transactional
  public UserResponse updatePassword(UpdateUserPasswordRequest request) {
    User user = SecurityUtil.getAuthenticatedUser();
    if (user.getPassword() != null && !passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
      throw ApiException.builder().status(400).message("Wrong password").build();
    }

    user.updatePassword(request.getPassword());
    user = userRepository.save(user);
    return new UserResponse(user);
  }

  public UserResponse updateProfilePicture(MultipartFile file) {
    User user = SecurityUtil.getAuthenticatedUser();
    UploadedFile uploadedFile = new UploadedFile(file.getOriginalFilename(), file.getSize(), user);
    try {
      String url = fileUploadService.uploadFile(
          uploadedFile.buildPath("profile-picture"),
          file.getBytes());
      uploadedFile.onUploaded(url);
      user.setProfileImageUrl(url);
      userRepository.save(user);
      uploadedFileRepository.save(uploadedFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return new UserResponse(user);
  }

  /**
   * Returns the entity ID object from the given DTO
   *
   * @param dto
   * @return
   */
  private Long getEntityId(UserDTO dto) {
    return dto.getId();
  }

  /**
   * Finds all occurrences of the entity
   *
   * @return
   */
  public List<UserDTO> findAll() {
    logger.debug("findAll()");
    Iterable<User> all = userRepository.findAll();
    return entityListToDtoList(all);
  }

  /**
   * Finds the entity identified by the given PK
   *
   * @param id
   * @return the entity or null if not found
   */
  public UserDTO findById(Long id) {
    Long entityId = id;
    logger.debug("findById({})", entityId);
    Optional<User> optionalEntity = userRepository.findById(entityId);
    return entityToDto(optionalEntity);
  }

  /**
   * Saves the given entity with the given PK <br>
   * "UPSERT" operation (updated if it exists or created if it does not exist)
   *
   * @param id
   * @param dto
   */
  public void save(Long id, UserDTO dto) {
    Long entityId = id;
    logger.debug("save({},{})", entityId, dto);
    // force PK in DTO (just to be sure to conform with the given PK)
    dto.setId(id);
    userRepository.save(dtoToEntity(dto));
  }

  /**
   * Updates the given entity if it exists
   *
   * @param dto
   * @return true if updated, false if not found
   */
  public boolean update(UserDTO dto) {
    logger.debug("update({})", dto);
    if (userRepository.existsById(getEntityId(dto))) {
      userRepository.save(dtoToEntity(dto));
      return true; // find and updated
    } else {
      return false; // not found (not updated)
    }
  }

  /**
   * Updates partially the given entity if it exists
   *
   * @param id
   * @param dto
   * @return true if updated, false if not found
   */
  public boolean partialUpdate(Long id, UserDTO dto) {
    Long entityId = id;
    logger.debug("partialUpdate({}, {})", entityId, dto);
    Optional<User> optionalEntity = userRepository.findById(entityId);
    if (optionalEntity.isPresent()) {
      User entity = optionalEntity.get();
      // implement here the partial update
      // entity.setXxx(dto.getXxx());
      // etc ...
      userRepository.save(entity);
      return true; // find and updated
    } else {
      return false; // not found (not updated)
    }
  }

  /**
   * Creates the given entity
   *
   * @param dto
   * @return true if created, false if already exists
   */
  public boolean create(UserDTO dto) {
    logger.debug("create({})", dto);
    // auto-generated Primary Key
    userRepository.save(dtoToEntity(dto));
    return true; // always created
  }

  /**
   * Deletes an entity by its PK
   *
   * @param id
   * @return true if deleted, false if not found
   */
  public boolean deleteById(Long id) {
    Long entityId = id;
    logger.debug("deleteById({})", entityId);
    if (userRepository.existsById(entityId)) {
      userRepository.deleteById(entityId);
      return true; // find and deleted
    } else {
      return false; // not found (not deleted)
    }
  }

}
