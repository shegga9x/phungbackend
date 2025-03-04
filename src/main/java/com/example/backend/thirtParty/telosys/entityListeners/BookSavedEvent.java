package com.example.backend.thirtParty.telosys.entityListeners;

public class BookSavedEvent {
    private final Long bookId;

    public BookSavedEvent(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookId() {
        return bookId;
    }
}
