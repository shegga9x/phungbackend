/*
 * Created on 2025-02-08 ( 14:50:02 )
 * Generated by Telosys ( https://www.telosys.org/ ) version 4.2.0
 */
package com.example.backend.thirtParty.telosys.persistence.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.example.backend.users.User;
import com.example.backend.util.Client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * JPA entity class for "Ratings"
 *
 * @author Telosys
 *
 */
@Entity
@Client
@Table(name = "ratings", catalog = "starter-kit-db")
@IdClass(RatingsId.class)
public class Ratings implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- PRIMARY KEY
    @Id
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // --- OTHER DATA FIELDS
    @Column(name = "score", nullable = false)
    private byte score;

    @Column(name = "rated_at", nullable = false)
    private LocalDateTime ratedAt;

    // --- LINKS ( RELATIONSHIPS )
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)

    private User users;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Books books;

    /**
     * Constructor
     */
    public Ratings() {
        super();
    }

    public Ratings(Long bookId, Long userId, byte score, LocalDateTime ratedAt) {
        this.bookId = bookId;
        this.userId = userId;
        this.score = score;
        this.ratedAt = ratedAt;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookId() {
        return this.bookId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setScore(byte score) {
        this.score = score;
    }

    public byte getScore() {
        return this.score;
    }

    public void setRatedAt(LocalDateTime ratedAt) {
        this.ratedAt = ratedAt;
    }

    public LocalDateTime getRatedAt() {
        return this.ratedAt;
    }

    public User getUser() {
        return this.users;
    }

    public Books getBooks() {
        return this.books;
    }

    @Override
    public String toString() {
        String separator = "|";
        StringBuilder sb = new StringBuilder();
        sb.append("Ratings[");
        sb.append("bookId=").append(bookId);
        sb.append(separator).append("userId=").append(userId);
        sb.append(separator).append("score=").append(score);
        sb.append(separator).append("ratedAt=").append(ratedAt);
        sb.append("]");
        return sb.toString();
    }

}
