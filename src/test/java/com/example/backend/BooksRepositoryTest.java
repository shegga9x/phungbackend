package com.example.backend;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.backend.ITBookAPI.ITBookService;
import com.example.backend.ITBookAPI.DTO.ITBookDTO;
import com.example.backend.ITBookAPI.DTO.ITBookDetailDTO;
import com.example.backend.telosys.persistence.repositories.BooksRepository;
import com.example.backend.telosys.rest.dto.AuthorsDTO;
import com.example.backend.telosys.rest.dto.BooksDTO;
import com.example.backend.telosys.rest.dto.BooksResponseDTO;
import com.example.backend.telosys.rest.services.BooksService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@jakarta.transaction.Transactional
public class BooksRepositoryTest {

	@Autowired
	private BooksRepository booksRepository;

	@Autowired
	private ITBookService iTBookService;

	@Autowired
	private BooksService booksService;
	// @Autowired
	// private AuthorsService authorsService;
	// @Autowired
	// private OrdersService ordersService;
	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void testFindBooksWithAuthorsAndAvgScore() {
		Pageable pageable = PageRequest.of(0, 10);
		List<BooksResponseDTO> results = booksService.findAllWithPagination(pageable, "Arts", "price");
		for (BooksResponseDTO result : results) {
			System.out.println(
					result.getId() + " Price: " + result.getPrice() + " Publish At: " + result.getPublishedAt());

		}
		return;
	}

	@Test
	public void testFindAllWithPagination() {
		System.out.println(booksRepository.countBooksWithAuthorsAndAvgScore(null));
	}

	// @Test
	// public void testITBookAPI() {
	// 	String url = "https://itbook.store/books";
	// 	List<String> categories = iTBookService.getBookCategories(url);
	// 	List<BooksDTO> booksDTOList = new ArrayList<>();
	// 	List<AuthorsDTO> authorsDTOList = new ArrayList<>();
	// 	Map<Long, List<Long>> book_authorsMap = new HashMap<Long, List<Long>>();
	// 	Long authorID = 21L;
	// 	for (String category : categories) {
	// 		String typeHref = category.split("&&")[0];
	// 		String typeText = category.split("&&")[1];
	// 		List<ITBookDTO> books = iTBookService.getAllBooks(typeHref);
	// 		for (ITBookDTO book : books) {
	// 			boolean bookExists = booksDTOList.stream()
	// 					.anyMatch(obj -> obj.getId().equals(Long.parseLong(book.getIsbn13())));
	// 			if (bookExists) {
	// 				continue;
	// 			}
	// 			BooksDTO bookDTO = new BooksDTO();
	// 			bookDTO.setTitle(book.getTitle());
	// 			bookDTO.setSubtitle(book.getSubtitle());
	// 			bookDTO.setId(Long.parseLong(book.getIsbn13()));
	// 			bookDTO.setPrice(new BigDecimal(book.getPrice().replace("$", "")));
	// 			// bookDTO.setUrl_Img((book.getImage()));
	// 			bookDTO.setType(typeText);
	// 			ITBookDetailDTO bookDetailDTO = iTBookService.getBookDetails(book.getIsbn13());
	// 			bookDetailDTO.getYear();
	// 			bookDTO.setPublishedAt(LocalDateTime.of(Integer.parseInt(bookDetailDTO.getYear()),
	// 					1, 1, 0, 0));
	// 			String[] authorNames = bookDetailDTO.getAuthors().split(",");
	// 			for (String authorName : authorNames) {
	// 				boolean authorsExists = authorsDTOList.stream()
	// 						.anyMatch(obj -> obj.getName().equals(authorName.trim()));
	// 				if (!authorsExists) {
	// 					AuthorsDTO authorDTO = new AuthorsDTO(authorID, authorName.trim());
	// 					authorsDTOList.add(authorDTO);
	// 					book_authorsMap.put(authorID, new ArrayList<>(Arrays.asList(bookDTO.getId())));
	// 					authorID++;
	// 				} else {
	// 					Long idAuthorsExists = authorsDTOList.stream()
	// 							.filter(obj -> obj.getName().equals(authorName.trim())).findFirst().get().getId();
	// 					List<Long> booksOfAuthoExist = book_authorsMap.get(idAuthorsExists);
	// 					booksOfAuthoExist.add(bookDTO.getId());
	// 					book_authorsMap.put(authorID, booksOfAuthoExist);
	// 				}
	// 			}
	// 			booksDTOList.add(bookDTO);
	// 		}
	// 		// break;
	// 	}

	// }

}
