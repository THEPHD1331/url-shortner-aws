package com.url.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Author: Paras Dongre
 * Date Created:16-03-2026
 * Time Created:21:22
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public ResponseEntity<?> homePage(){
        return ResponseEntity.ok(Map.of("status", "URL shortner App is UP and running!"));
    }
}
