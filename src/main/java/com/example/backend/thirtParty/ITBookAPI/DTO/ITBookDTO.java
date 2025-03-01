package com.example.backend.thirtParty.ITBookAPI.DTO;

public class ITBookDTO {
    private String title;
    private String subtitle;
    private String isbn13;
    private String price;
    private String image;
    private String url;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ITBookDTO [title=" + title + ", subtitle=" + subtitle + ", isbn13=" + isbn13 + ", price=" + price
                + ", image=" + image + ", url=" + url + "]";
    }

}
