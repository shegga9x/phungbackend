/*
 * Created on 2025-02-08 ( 14:50:02 )
 * Generated by Telosys ( https://www.telosys.org/ ) version 4.2.0
 */
package com.example.backend.thirtParty.telosys.rest.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BaseHttpSolrClient.RemoteSolrException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.backend.thirtParty.solr.crud.BookSolrService;
import com.example.backend.thirtParty.telosys.persistence.entities.Books;
import com.example.backend.thirtParty.telosys.persistence.repositories.BooksRepository;
import com.example.backend.thirtParty.telosys.rest.dto.BooksDTO;
import com.example.backend.thirtParty.telosys.rest.dto.BooksResponseDTO;
import com.example.backend.thirtParty.telosys.rest.services.commons.GenericService;

/**
 * REST service for entity "Books" <br>
 * 
 * This service provides all the necessary operations required by the REST
 * controller <br>
 * 
 * @author Telosys
 *
 */
@Service
public class BooksService extends GenericService<Books, BooksDTO> {

	private static final Logger logger = LoggerFactory.getLogger(BooksService.class);

	private final BooksRepository repository; // injected by constructor
	private final BookSolrService bookSolrService; // injected by constructor

	/**
	 * Constructor (usable for Dependency Injection)
	 * 
	 * @param repository the repository to be injected
	 */
	public BooksService(BooksRepository repository, BookSolrService bookSolrService) {
		super(Books.class, BooksDTO.class);
		this.repository = repository;
		this.bookSolrService = bookSolrService;

	}

	/**
	 * Returns the entity ID object from the given DTO
	 *
	 * @param dto
	 * @return
	 */
	private Long getEntityId(BooksDTO dto) {
		return dto.getId();
	}

	/**
	 * Finds all occurrences of the entity
	 *
	 * @return
	 */
	public List<BooksDTO> findAll() {
		logger.debug("findAll()");
		Iterable<Books> all = repository.findAll();
		return entityListToDtoList(all);
	}

	/**
	 * Finds the entity identified by the given PK
	 *
	 * @param id
	 * @return the entity or null if not found
	 */
	public BooksDTO findById(Long id) {
		Long entityId = id;
		logger.debug("findById({})", entityId);
		Optional<Books> optionalEntity = repository.findById(entityId);
		return entityToDto(optionalEntity);
	}

	/**
	 * Saves the given entity with the given PK <br>
	 * "UPSERT" operation (updated if it exists or created if it does not exist)
	 *
	 * @param id
	 * @param dto
	 */
	public void save(Long id, BooksDTO dto) {
		Long entityId = id;
		logger.debug("save({},{})", entityId, dto);
		// force PK in DTO (just to be sure to conform with the given PK)
		dto.setId(id);
		repository.save(dtoToEntity(dto));
	}

	/**
	 * Saves all given entities
	 *
	 * @param dtos
	 */
	public void saveAll(List<BooksDTO> dtos) {
		logger.debug("saveAll({})", dtos);
		List<Books> entities = dtoListToEntityList(dtos);
		repository.saveAll(entities);
	}

	/**
	 * Updates the given entity if it exists
	 *
	 * @param dto
	 * @return true if updated, false if not found
	 */
	public boolean update(BooksDTO dto) {
		logger.debug("update({})", dto);
		if (repository.existsById(getEntityId(dto))) {
			repository.save(dtoToEntity(dto));
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
	public boolean partialUpdate(Long id, BooksDTO dto) {
		Long entityId = id;
		logger.debug("partialUpdate({}, {})", entityId, dto);
		Optional<Books> optionalEntity = repository.findById(entityId);
		if (optionalEntity.isPresent()) {
			Books entity = optionalEntity.get();
			// implement here the partial update
			// entity.setXxx(dto.getXxx());
			// etc ...
			repository.save(entity);
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
	public boolean create(BooksDTO dto) {
		logger.debug("create({})", dto);
		// auto-generated Primary Key
		repository.save(dtoToEntity(dto));
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
		if (repository.existsById(entityId)) {
			repository.deleteById(entityId);
			return true; // find and deleted
		} else {
			return false; // not found (not deleted)
		}
	}

	/**
	 * Finds all occurrences of the entity with pagination
	 *
	 * @param pageable
	 * @return
	 */
	public List<BooksResponseDTO> findAllWithPagination(Pageable pageable, String bookType, String flag, String title) {
		logger.debug("findAllWithPagination({}, {}, {})", pageable, bookType, flag);
		if (pageable.getPageNumber() > 0) {
			if (flag != null) {
				Sort sort = Sort.by(Sort.Direction.fromString(Direction.DESC.name()), flag);
				pageable = PageRequest.of(pageable.getPageNumber() - 1, 6, sort);
			} else {
				pageable = PageRequest.of(pageable.getPageNumber() - 1, 6);
			}
		}
		try {
			return bookSolrService.findBooksWithAuthorsAndRatings(pageable, bookType, flag, title);
		} catch (SolrServerException | IOException | RemoteSolrException e) {
			System.out.println(e);
			return mapObjectArrayToDTO(repository.findBooksWithAuthorsAndRatings(pageable, bookType, title));
		}

	}

	public List<BooksResponseDTO> findBooksWithAuthorsAndRatings() {
		return mapObjectArrayToDTO(repository.findBooksWithAuthorsAndRatings(null, null, null));
	}

	/**
	 * Finds all occurrences of the entity with pagination
	 *
	 * @param pageable
	 * @return
	 */
	public BooksResponseDTO findBooksWithAuthorsAndRatingsById(Long id) {
		return mapObjectToDTO(repository.findBooksWithAuthorsAndRatingsById(id));
	}

	/**
	 * Returns the total number of books with authors and average score
	 *
	 * @param bookType the type of the book
	 * @return the total count of books with authors and average score
	 */
	public long getTotalBooksWithAuthorsAndAvgScore(String bookType, String title) {
		logger.debug("getTotalBooks()");
		try {
			return bookSolrService.getTotalDocuments(bookType, title);
		} catch (SolrServerException | IOException | RemoteSolrException e) {
			return repository.countBooksWithAuthorsAndAvgScore(bookType, title);

		}
	}

	// -----------------------------------------------------------------------------------------
	// Specific "finders"
	// -----------------------------------------------------------------------------------------
	/***
	 * public List<BooksDTO> findByTitle(String title) {
	 * logger.debug("findByTitle({})", title);
	 * // List<Books> list = repository.findByTitle(title);
	 * List<Books> list = repository.findByTitleContaining(title);
	 * return entityListToDtoList(list);
	 * }
	 * 
	 * public List<BooksDTO> findByPrice(BigDecimal price) {
	 * logger.debug("findByPrice({})", price);
	 * // List<Books> list = repository.findByTitle(title);
	 * List<Books> list = repository.findByPrice(price);
	 * return entityListToDtoList(list);
	 * }
	 * 
	 * public List<BooksDTO> findByTitleAndPrice(String title, BigDecimal price) {
	 * logger.debug("findByTitleAndPrice({}, {})", title, price);
	 * List<Books> list = repository.findByTitleContainingAndPrice(title, price);
	 * return entityListToDtoList(list);
	 * }
	 ***/

	public List<String> search(String title) {
		try {
			List<BooksResponseDTO> books = bookSolrService.searchBooks(title);
			return books.stream().map(BooksResponseDTO::getTitle).collect(Collectors.toList());
		} catch (SolrServerException | IOException | RemoteSolrException e) {
			System.out.println(e);
			return repository.findBooksWithTitle(title);
		}
	}
}
