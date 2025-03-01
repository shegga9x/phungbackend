package com.example.backend.thirtParty.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class SolrService {
    private static final String SOLR_URL = "http://localhost:8983/solr/books";
    private final SolrClient solrClient;

    @SuppressWarnings("deprecation")
    public SolrService() {
        this.solrClient = new HttpSolrClient.Builder(SOLR_URL) // Pass URL in constructor
                .build();
    }

    public void addBookToSolr(Object[] object) throws Exception {
        solrClient.add(BookSolrDocument.fromBook(object));
        solrClient.commit();
    }

    public void addBooksToSolr(List<Object[]> objects) throws Exception {
        for (Object[] object : objects) {
            solrClient.add(BookSolrDocument.fromBook(object));
        }
        solrClient.commit();
    }
}
