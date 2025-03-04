package com.example.backend.thirtParty.solr.migration;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.apache.solr.common.SolrInputDocument;

public class BookSolrDocument {
    public static SolrInputDocument fromBook(Object[] objects) {

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", (Long) objects[0]);
        doc.addField("title", (String) objects[1]);
        doc.addField("type", (String) objects[2]);

        // Convert Timestamp to LocalDateTime, then to Date for Solr
        Timestamp sqlTime = (Timestamp) objects[3];
        LocalDateTime localDateTime = sqlTime.toLocalDateTime();
        doc.addField("publishedAt", Date.from(localDateTime.toInstant(ZoneOffset.UTC)));

        doc.addField("stock", (Integer) objects[4]);
        doc.addField("price", objects[5] != null ? ((BigDecimal) objects[5]).doubleValue() : 0.0);
        doc.addField("urlImg", (String) objects[7]);
        doc.addField("averageRating", objects[8] != null ? ((BigDecimal) objects[8]).doubleValue() : 0.0);
        doc.addField("authors", objects[9] != null ? ((String) objects[9]).split(",") : new String[] { "" });
        return doc;
    }
}
