package com.url.service;

import com.url.entity.URL;

import java.util.List;

public interface URLService {

    String shortenUrl(String originalUrl);
    String getOriginalUrl(String shortCode);
    List<URL> getAllUrls();
}
