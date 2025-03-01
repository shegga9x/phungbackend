package com.example.backend.thirtParty.telosys.persistence.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

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

@Entity
@Client
@Table(name = "cartItem", catalog = "starter-kit-db")
@IdClass(CartItemId.class)
public class CartItem {
    @Id
    @Column(name = "book_id", nullable = false)
    private Long bookId;
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // --- LINKS ( RELATIONSHIPS )
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User users;
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Books books;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

}
