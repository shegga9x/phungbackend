package com.example.backend.thirtParty.ITBookAPI.DTO;

import java.util.List;

public class ITBookResponseDTO {
    private int total;
    private List<ITBookDTO> books;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ITBookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<ITBookDTO> books) {
        this.books = books;
    }

}
