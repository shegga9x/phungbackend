package com.example.backend.thirtParty.solr.migration;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.backend.thirtParty.solr.SolrService;
import com.example.backend.thirtParty.telosys.persistence.repositories.BooksRepository;

@Service
public class BookMigrationService {
    private final BooksRepository bookRepository;
    private final SolrService solrService;

    public BookMigrationService(SolrService solrService, BooksRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.solrService = solrService;
    }

    public void migrateBooksToSolr() throws Exception {
        List<Object[]> books = bookRepository.findBooksWithAuthorsAndRatings(null, null, null);
        solrService.addBooksToSolr(books);
    }
}
