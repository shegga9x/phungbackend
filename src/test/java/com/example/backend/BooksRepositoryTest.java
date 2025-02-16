package com.example.backend;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.backend.telosys.persistence.repositories.BooksRepository;
import com.example.backend.telosys.rest.dto.BooksResponseDTO;
import com.example.backend.telosys.rest.dto.OrdersDTO;
import com.example.backend.telosys.rest.services.BooksService;
import com.example.backend.telosys.rest.services.OrdersService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@jakarta.transaction.Transactional
public class BooksRepositoryTest {

	@Autowired
	private BooksRepository booksRepository;

	@Autowired
	private BooksService booksService;
	@Autowired
	private OrdersService ordersService;
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
		// Pageable pageable = PageRequest.of(1, 10);
		// List<Object[]> results =
		// booksRepository.findBooksWithAuthorsAndAvgScore(pageable);
		// for (Object[] result : results) {
		// int id = (int) result[0];
		// String title = (String) result[1];
		// String description = (String) result[2];
		// java.sql.Timestamp isbn = (java.sql.Timestamp) result[3];
		// int pageCount = (int) result[4];
		// BigDecimal thumbnailUrl = (BigDecimal) result[5];
		// String authors = (String) result[6];
		// BigDecimal avgScore = (BigDecimal) result[7];

		// System.out.println("ID: " + id);
		// System.out.println("Title: " + title);
		// System.out.println("Description: " + description);
		// System.out.println("ISBN: " + isbn);
		// System.out.println("Page Count: " + pageCount);
		// System.out.println("Thumbnail URL: " + thumbnailUrl);
		// System.out.println("Authors: " + authors);
		// System.out.println("Average Score: " + avgScore);
		// assertNotNull(authors);
		// assertNotNull(avgScore);
		return;
	}

	@Test
	public void testFindAllWithPagination() {
		System.out.println(booksRepository.countBooksWithAuthorsAndAvgScore(null));
	}

}
