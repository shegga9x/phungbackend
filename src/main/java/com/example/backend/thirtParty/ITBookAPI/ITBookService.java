package com.example.backend.thirtParty.ITBookAPI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.example.backend.thirtParty.ITBookAPI.DTO.ITBookDTO;
import com.example.backend.thirtParty.ITBookAPI.DTO.ITBookDetailDTO;
import com.example.backend.thirtParty.ITBookAPI.DTO.ITBookResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ITBookService {
    private final ITBookApiClient iTBookApiClient;

    public ITBookService(ITBookApiClient iTBookApiClient) {
        this.iTBookApiClient = iTBookApiClient;
    }

    public List<ITBookDTO> getAllBooks(String type) {
        ITBookResponseDTO response = iTBookApiClient.getBooks(type, 1);
        int total = response.getTotal();
        int lastPage = (int) Math.ceil((double) total / 10);
        if (lastPage > 100) {
            lastPage = 100;
        }
        List<ITBookDTO> books = response.getBooks();
        for (int i = 2; i <= lastPage; i++) {
            books.addAll(iTBookApiClient.getBooks(type, i).getBooks());
        }

        return books;
    }

    public List<ITBookDTO> getBooks(String type, int page) {
        ITBookResponseDTO response = iTBookApiClient.getBooks(type, page);
        return response.getBooks();
    }

    public ITBookDetailDTO getBookDetails(String isbn13) {
        return iTBookApiClient.getBookDetails(isbn13);
    }

    public List<String> getBookCategories(String url) {
        List<String> categories = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements categoryElements = doc.select("div.col-md-3 a");

            for (Element category : categoryElements) {
                Elements categoryElementsHref = category.select("a");
                String categoryTextHref = categoryElementsHref.attr("href").replace("/books/", "").trim();
                String categoryText = categoryElementsHref.text().trim();
                categories.add(categoryTextHref + "&&" + categoryText);
            }
        } catch (IOException e) {
            System.out.println("Failed to retrieve the webpage: " + e.getMessage());
        }
        return categories;
    }

    public static void saveToJson(List<String> data, String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(Files.newBufferedWriter(Paths.get(filename)), data);
            System.out.println("Categories saved to " + filename);
        } catch (IOException e) {
            System.out.println("Failed to save categories: " + e.getMessage());
        }
    }
}
