package com.example.backend.thirtParty.solr.crud;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BaseHttpSolrClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.apache.solr.client.solrj.impl.BaseHttpSolrClient.RemoteSolrException;

import com.example.backend.thirtParty.solr.SolrService;
import com.example.backend.thirtParty.telosys.rest.dto.BooksResponseDTO;

import java.io.IOException;
import java.util.List;

@Service
public class BookSolrService {
    private final SolrService solrService;

    public BookSolrService(SolrService solrService) {
        this.solrService = solrService;
    }

    public void saveBook(BooksResponseDTO book) {
        try {
            solrService.save(book);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<BooksResponseDTO> searchBooks(String title) {
        try {
            return solrService.searchByTitle(title);
        } catch (SolrServerException | IOException | RemoteSolrException e) {
            throw new RuntimeException("Error searching books", e);
        }
    }

    public List<BooksResponseDTO> findBooksWithAuthorsAndRatings(Pageable pageable, String bookType, String flag,
            String title) {
        try {
            if (flag != null && flag.equals("price")) {
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by("price").descending());
            } else {
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by("publishedAt").descending());
            }
            return solrService.findBooksWithAuthorsAndRatings(pageable, bookType, title);
        } catch (SolrServerException | IOException | RemoteSolrException e) {
            System.out.println(e);
            throw new RuntimeException("Error searching books", e);
        }
    }

    public long getTotalDocuments(String bookType, String title) {
        try {
            return solrService.getTotalDocuments(bookType, title);
        } catch (SolrServerException | IOException e) {
            throw new RuntimeException("Error searching books", e);

        }
    }
}
