package com.example.backend.thirtParty.openSearch;

import org.opensearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/openSearch")

public class SearchController {

    @Autowired
    private OpenSearchService searchService;

    @GetMapping()
    public String search(@RequestParam String query) throws Exception {
        SearchResponse response = searchService.search(query);
        return response.toString(); // Or parse and return specific results
    }
}