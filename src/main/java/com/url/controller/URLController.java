package com.url.controller;

import com.url.dto.UrlRequest;
import com.url.repository.URLRepository;
import com.url.service.URLService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

/**
 * Author: Paras Dongre
 * Date Created:18-02-2026
 * Time Created:21:31
 */
@RestController
@RequestMapping("/url/v1")
@RequiredArgsConstructor
public class URLController {

    private final URLService urlService;
    private final URLRepository urlRepository;

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody UrlRequest urlRequest){

        String shortUrl = urlService.shortenUrl(urlRequest.getOriginalUrl());
        return ResponseEntity.ok(Map.of("status", "SUCCESS", "shortUrl", shortUrl));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> goToOriginalUrl(@PathVariable String shortCode){

        try {
            String originalUrl = urlService.getOriginalUrl(shortCode);
            if (originalUrl != null && !originalUrl.isBlank()){
                urlRepository.incrementClickCount(shortCode);
            }
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(originalUrl))
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "FAILURE", "data", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUrls(){
        return ResponseEntity.ok(Map.of("status", "SUCCESS", "data", urlService.getAllUrls()));
    }
}
