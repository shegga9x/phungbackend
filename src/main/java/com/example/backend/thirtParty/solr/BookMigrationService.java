package com.example.backend.thirtParty.solr;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.backend.thirtParty.telosys.persistence.repositories.BooksRepository;

@Service
public class BookMigrationService {
    private final BooksRepository bookRepository;
    private final SolrService solrService;

    public BookMigrationService(BooksRepository bookRepository, SolrService solrService) {
        this.bookRepository = bookRepository;
        this.solrService = solrService;
    }

    public void migrateBooksToSolr() throws Exception {
        List<Object[]> books = bookRepository.findBooksWithAuthorsAndRatings();
        solrService.addBooksToSolr(books);
    }
}
