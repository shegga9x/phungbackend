package com.example.backend.thirtParty.telosys.entityListeners;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;
import com.example.backend.thirtParty.solr.SolrService;
import com.example.backend.thirtParty.telosys.rest.dto.BooksResponseDTO;
import com.example.backend.thirtParty.telosys.rest.services.BooksService;

@Service
public class BookEventListener {

    private final BooksService booksService;
    private final SolrService solrService;

    public BookEventListener(BooksService booksService, SolrService solrService) {
        this.booksService = booksService;
        this.solrService = solrService;
    }

    @Async // ✅ Runs in the background (prevents blocking)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBookSaved(BookSavedEvent event) {
        System.out.println("🔄 Updating Solr for book ID: " + event.getBookId());
        BooksResponseDTO book = booksService.findBooksWithAuthorsAndRatingsById(event.getBookId());
        try {
            solrService.save(book);
        } catch (SolrServerException | IOException e) {
            System.out.println("❌ Failed to index book in Solr: " + e.getMessage());
        }
    }
}
