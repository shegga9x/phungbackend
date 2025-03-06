package com.example.backend.thirtParty.telosys.rest.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrDocument;

public class BooksResponseDTO {
    private Long id;
    private String title;
    private String type;
    private LocalDateTime publishedAt;
    private int stock;
    private BigDecimal price;
    private String[] authors;
    private String urlImg;

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

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public BooksResponseDTO mapSolrDocumentToDTO(SolrDocument doc) {
        BooksResponseDTO dto = new BooksResponseDTO();
        Object idObj = doc.getFieldValue("id");
        if (idObj != null) {
            dto.setId(Long.parseLong(idObj.toString())); // Convert String to Long
        }

        dto.setTitle(((List<String>) doc.getFieldValue("title")).get(0));
        dto.setType((String) doc.getFieldValue("type"));

        // Convert Solr Date (stored as java.util.Date) to LocalDateTime
        Object publishedAtObj = doc.getFieldValue("publishedAt");
        if (publishedAtObj != null && publishedAtObj instanceof java.util.Date) {
            dto.setPublishedAt(((java.util.Date) publishedAtObj).toInstant().atZone(ZoneOffset.UTC).toLocalDateTime());
        }
        Object stockObj = doc.getFieldValue("stock");
        if (stockObj != null) {
            dto.setStock(((Long) stockObj).intValue());
        }

        // Convert price and rating back to BigDecimal
        Object priceObj = doc.getFieldValue("price");
        if (priceObj != null) {
            dto.setPrice(new BigDecimal(priceObj.toString()));
        }

        Object ratingObj = doc.getFieldValue("averageRating");
        if (ratingObj != null) {
            dto.setAverageRating(new BigDecimal(ratingObj.toString()));
        }
        dto.setUrlImg((String) doc.getFieldValue("urlImg"));

        Object authorsObj = doc.getFieldValue("authors");
        if (authorsObj instanceof String[]) {
            dto.setAuthors((String[]) authorsObj);
        } else if (authorsObj instanceof String) {
            dto.setAuthors(new String[] { (String) authorsObj });
        } else {
            dto.setAuthors(new String[] { "" });
        }

        return dto;
    }
}
