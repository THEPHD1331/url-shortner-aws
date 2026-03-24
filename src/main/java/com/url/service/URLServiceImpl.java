package com.url.service;

import com.url.entity.URL;
import com.url.repository.URLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

/**
 * Author: Paras Dongre
 * Date Created:18-02-2026
 * Time Created:22:29
 */
@Service
@RequiredArgsConstructor
public class URLServiceImpl implements URLService{

    private final URLRepository urlRepository;
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    public String shortenUrl(String originalUrl) {

        Optional<URL> existingUrl = urlRepository.findByOriginalUrl(originalUrl);
        if (existingUrl.isPresent()){
            return existingUrl.get().getShortCode();
        }

        LocalDateTime ldt = LocalDateTime.now();
        //  Use nanoseconds instead of seconds
        long timeStamp = ldt.toInstant(ZoneOffset.UTC).toEpochMilli() * 1_000_000 + ldt.getNano();
        //  Mix in a random salt to survive same-nanosecond collisions
        long uniqueSeed = timeStamp ^ (long)(Math.random() * Long.MAX_VALUE);
        //  Take only the last 8 chars to keep URLs short
        String shortCode = encodeIdByBase62(Math.abs(uniqueSeed)).substring(0, 8);

        URL url = new URL();
        url.setId(timeStamp);
        url.setShortCode(shortCode);
        url.setOriginalUrl(originalUrl);
        url.setCreatedAt(ldt);
        url.setExpiryAt(ldt.plusDays(7));
        url.setClickCount(0);

        urlRepository.save(url);
        return url.getShortCode();
    }

    @Override
    public String getOriginalUrl(String shortCode) {

        URL url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("No URL found for short code: "+shortCode));
        return url.getOriginalUrl();
    }

    @Override
    public List<URL> getAllUrls() {
        return urlRepository.findAll();
    }

    private String encodeIdByBase62(long id) {
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.append(BASE62.charAt((int) (id % 62)));
            id /= 62;
        }
        return sb.reverse().toString();
    }
}
