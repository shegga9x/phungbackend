package com.example.backend.thirtParty.solr;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.BaseHttpSolrClient.RemoteSolrException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.backend.config.ApplicationProperties;
import com.example.backend.thirtParty.solr.migration.BookSolrDocument;
import com.example.backend.thirtParty.telosys.rest.dto.BooksResponseDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SolrService {

    private final SolrClient solrClient;

    @SuppressWarnings("deprecation")
    public SolrService(ApplicationProperties applicationProperties) {

        // Set up authentication credentials
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(applicationProperties.getSolrUsername(),
                        applicationProperties.getSolrPassword()));

        // Create HTTP client with authentication
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(provider)
                .setDefaultRequestConfig(requestConfig)
                .addInterceptorFirst((HttpRequestInterceptor) (request, context) -> {
                    request.addHeader("Authorization",
                            "Basic " + java.util.Base64.getEncoder().encodeToString(
                                    (applicationProperties.getSolrUsername() + ":"
                                            + applicationProperties.getSolrPassword())
                                            .getBytes()));
                })
                .setRetryHandler(new DefaultHttpRequestRetryHandler(3, true)) // Allow retries
                .build();

        // Initialize Solr client with authentication
        this.solrClient = new HttpSolrClient.Builder(applicationProperties.getSolrUrl())
                .withHttpClient(httpClient)
                .build();
    }

    public void addBookToSolr(Object[] object) throws Exception {
        solrClient.add("books", BookSolrDocument.fromBook(object));
        solrClient.commit();
    }

    public void addBooksToSolr(List<Object[]> objects) throws Exception {
        for (Object[] object : objects) {
            solrClient.add("books", BookSolrDocument.fromBook(object));
        }
        solrClient.commit();
    }

    public List<BooksResponseDTO> searchByTitle(String title) throws SolrServerException, IOException {
        SolrQuery query = getSolrQuery(null, null, title);
        query.setRows(1000);
        QueryResponse response = solrClient.query("books", query);
        SolrDocumentList docs = response.getResults();
        List<BooksResponseDTO> results = new ArrayList<>();
        for (SolrDocument doc : docs) {
            BooksResponseDTO book = new BooksResponseDTO();
            results.add(book.mapSolrDocumentToDTO(doc));

        }
        return results;
    }

    public List<BooksResponseDTO> findBooksWithAuthorsAndRatings(Pageable pageable, String bookType, String title)
            throws SolrServerException, IOException,
            RemoteSolrException {
        SolrQuery query = getSolrQuery(pageable, bookType, title);
        QueryResponse response = solrClient.query("books", query);
        SolrDocumentList docs = response.getResults();
        List<BooksResponseDTO> results = new ArrayList<>();
        for (SolrDocument doc : docs) {
            BooksResponseDTO book = new BooksResponseDTO();
            results.add(book.mapSolrDocumentToDTO(doc));
        }
        return results;
    }

    public void save(BooksResponseDTO book) throws SolrServerException, IOException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", book.getId());
        doc.addField("title", book.getTitle());
        doc.addField("type", book.getType());
        doc.addField("publishedAt", book.getPublishedAt() != null ? book.getPublishedAt().toString() : null);
        doc.addField("stock", book.getStock());
        doc.addField("authors", book.getAuthors());
        doc.addField("averageRating", book.getAverageRating());
        doc.addField("urlImg", book.getUrlImg());
        solrClient.add("books", doc);
        solrClient.commit("books");
    }

    public long getTotalDocuments(String bookType, String title) throws SolrServerException, IOException {
        SolrQuery query = getSolrQuery(null, bookType, title);
        query.setRows(0);
        QueryResponse response = solrClient.query("books", query);
        return response.getResults().getNumFound();

    }

    public SolrQuery getSolrQuery(Pageable pageable, String bookType, String title) {
        SolrQuery query = new SolrQuery();
        // Filtering by book type
        StringBuilder queryStr = new StringBuilder();
        if (bookType != null && !bookType.isEmpty()) {
            queryStr.append("type:\"").append(bookType).append("\" ");
        }
        if (title != null && !title.isEmpty()) {
            queryStr.append("title:*").append(title).append("*^2 OR title:").append(title).append("~"); // Boost exact +
            query.set("qf", "title");
            query.set("defType", "edismax");
            query.set("mm", "1");
        }
        if (queryStr.length() > 0) {
            query.setQuery(queryStr.toString());
        } else {
            query.setQuery("*:*");
        }
        if (pageable != null) {
            query.setStart((int) pageable.getOffset());
            query.setRows(pageable.getPageSize());
            pageable.getSort().forEach(order -> {
                query.addSort(order.getProperty(),
                        order.isAscending() ? SolrQuery.ORDER.asc : SolrQuery.ORDER.desc);
            });
        }
        return query;
    }

}