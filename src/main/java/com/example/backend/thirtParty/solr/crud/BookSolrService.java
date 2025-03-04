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

    public void saveBook(BooksResponseDTO book) throws SolrServerException, IOException {
        solrService.save(book);
    }

    public List<BooksResponseDTO> searchBooks(String title)
            throws SolrServerException, IOException, BaseHttpSolrClient.RemoteSolrException {
        return solrService.searchByTitle(title);
    }

    public List<BooksResponseDTO> findBooksWithAuthorsAndRatings(Pageable pageable, String bookType, String flag,
            String title)
            throws SolrServerException, IOException, RemoteSolrException {
        if (flag != null && flag.equals("price")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by("price").descending());
        } else {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by("publishedAt").descending());
        }
        return solrService.findBooksWithAuthorsAndRatings(pageable, bookType, flag, title);
    }

    public long getTotalDocuments(String bookType, String title)
            throws SolrServerException, IOException {
        return solrService.getTotalDocuments(bookType, title);
    }
}
