package com.example.backend.telosys.persistence.repositories;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class BookWithAuthorsAndAvgScoreDTO {
    private int id;
    private String title;
    private String type;
    private LocalDateTime publishedAt;
    private int stock;
    private BigDecimal price;
    private String authors;
    private Double averageScore;

public BookWithAuthorsAndAvgScoreDTO(int id, String title, String type, LocalDateTime publishedAt, int stock, BigDecimal price, String authors, Double averageScore) {
    this.id = id;
    this.title = title;
    this.type = type;
    this.publishedAt = publishedAt;
    this.stock = stock;
    this.price = price;
    this.authors = authors;
    this.averageScore = averageScore;
}

public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}

public String getTitle() {
    return title;
}

public void setTitle(String title) {
    this.title = title;
}

public String getType() {
    return type;
}

public void setType(String type) {
    this.type = type;
}

public LocalDateTime getPublishedAt() {
    return publishedAt;
}

public void setPublishedAt(LocalDateTime publishedAt) {
    this.publishedAt = publishedAt;
}

public int getStock() {
    return stock;
}

public void setStock(int stock) {
    this.stock = stock;
}

public BigDecimal getPrice() {
    return price;
}

public void setPrice(BigDecimal price) {
    this.price = price;
}

public String getAuthors() {
    return authors;
}

public void setAuthors(String authors) {
    this.authors = authors;
}

public Double getAverageScore() {
    return averageScore;
}

public void setAverageScore(Double averageScore) {
    this.averageScore = averageScore;
}

}
