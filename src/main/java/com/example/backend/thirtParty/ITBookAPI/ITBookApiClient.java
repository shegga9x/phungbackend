package com.example.backend.thirtParty.ITBookAPI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.backend.thirtParty.ITBookAPI.DTO.ITBookDetailDTO;
import com.example.backend.thirtParty.ITBookAPI.DTO.ITBookResponseDTO;

@FeignClient(name = "iTBookApi", url = "https://api.itbook.store/1.0/")
public interface ITBookApiClient {

        @GetMapping("/search/{type}/{page}")
        ITBookResponseDTO getBooks(@PathVariable String type, @PathVariable int page);

        @GetMapping("/books/{isbn13}")
        ITBookDetailDTO getBookDetails(@PathVariable String isbn13);
}
