package com.example.backend.thirtParty.telosys.entityListeners;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import com.example.backend.thirtParty.telosys.persistence.entities.Books;

@Component
public class BookListener {

    @Autowired
    private ApplicationEventPublisher eventPublisher; // ✅ Inject Spring Event Publisher

    @PostPersist
    @PostUpdate
    public void afterSaveOrUpdate(Books book) {
        System.out.println("📌 Book persisted/updated: " + book.getTitle());
        // ✅ Publish an event instead of calling SolrService directly
        eventPublisher.publishEvent(new BookSavedEvent(book.getId()));
    }
}
