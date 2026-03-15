package com.url.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Author: Paras Dongre
 * Date Created:15-02-2026
 * Time Created:23:44
 */
@Getter
@Setter
@ToString
@Entity
public class URL {

    @Id
    private long id;
    private String originalUrl;
    private String shortCode;
    private LocalDateTime createdAt;
    private LocalDateTime expiryAt;
    private long clickCount;

}
