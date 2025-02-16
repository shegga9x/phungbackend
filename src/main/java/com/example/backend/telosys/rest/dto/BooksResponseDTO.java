package com.example.backend.telosys.rest.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BooksResponseDTO {
    private Long id;
    private String title;
    private String type;
    private LocalDateTime publishedAt;
    private int stock;

    private BigDecimal price;
    private String[] authors;

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    private BigDecimal averageRating;

    public BooksResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    };

    protected List<BooksResponseDTO> mapObjectArrayToDTO(List<Object[]> objects) {
        List<BooksResponseDTO> result = new ArrayList<>();

        for (Object[] row : objects) {
            BooksResponseDTO dto = new BooksResponseDTO();
            if (row.length >= 8) {
                dto.setId((Long) row[0]);
                dto.setTitle((String) row[1]);
                dto.setType((String) row[2]);
                Timestamp sqlTime = (Timestamp) row[3];
                sqlTime.toLocalDateTime().toLocalDate();
                dto.setPublishedAt(sqlTime.toLocalDateTime());
                dto.setStock((Integer) row[4]);
                dto.setPrice((BigDecimal) row[5]);
                dto.setAuthors(((String) row[6]).split(","));
                dto.setAverageRating((BigDecimal) row[7]);
            }

            result.add(dto);
        }

        return result;
    }

}
