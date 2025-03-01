package com.example.backend.thirtParty.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import java.io.IOException;

public class SolrDeleteAll {
    public static void main(String[] args) {
        String solrUrl = "http://localhost:8983/solr/books"; // Replace with your core name
        try (SolrClient solrClient = new HttpSolrClient.Builder(solrUrl).build()) {
            solrClient.deleteByQuery("*:*"); // Delete all documents
            solrClient.commit(); // Commit the changes
            System.out.println("All documents deleted successfully.");
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }
}
