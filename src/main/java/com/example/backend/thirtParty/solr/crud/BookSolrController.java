package com.example.backend.thirtParty.solr.crud;

import org.springframework.web.bind.annotation.*;

import com.example.backend.thirtParty.telosys.rest.dto.BooksResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/solr")
public class BookSolrController {
    private final BookSolrService bookSolrService;

    public BookSolrController(BookSolrService bookSolrService) {
        this.bookSolrService = bookSolrService;
    }

    @PostMapping("/save")
    public String saveBook(@RequestBody BooksResponseDTO book) {
        try {
            bookSolrService.saveBook(book);
            return "Book saved to Solr!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/search")
    public List<BooksResponseDTO> searchBooks(@RequestParam String title) {
        try {
            return bookSolrService.searchBooks(title);
        } catch (Exception e) {
            System.out.println(e);
            return List.of();
        }
    }
}
