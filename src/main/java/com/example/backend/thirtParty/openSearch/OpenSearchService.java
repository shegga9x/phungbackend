package com.example.backend.thirtParty.openSearch;

import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenSearchService {

    @Autowired
    private RestHighLevelClient client;

    public SearchResponse search(String query) throws Exception {
        SearchRequest searchRequest = new SearchRequest("your-index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("content", query));
        searchRequest.source(searchSourceBuilder);

        return client.search(searchRequest, RequestOptions.DEFAULT);
    }
}