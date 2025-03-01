package com.example.backend.thirtParty.solr;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.apache.solr.common.SolrInputDocument;


public class BookSolrDocument {
    public static SolrInputDocument fromBook(Object[] objects) {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", (Long) objects[0]);
        doc.addField("title", (String) objects[1]);
        Timestamp sqlTime = (Timestamp) objects[3];
        doc.addField("publishedAt", sqlTime.toLocalDateTime());
        doc.addField("stock", (Integer) objects[4]);
        doc.addField("authors", objects[9] != null ? ((String) objects[9]).split(",") : new String[] { "" });
        doc.addField("type", (String) objects[2]);
        doc.addField("averageRating", objects[8] != null ? (BigDecimal) objects[8] : BigDecimal.ZERO);
        doc.addField("urlImg", (String) objects[7]);
        return doc;
    }
}
