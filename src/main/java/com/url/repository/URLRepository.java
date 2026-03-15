package com.url.repository;

import com.url.entity.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface URLRepository extends JpaRepository<URL, Long> {

    Optional<URL> findByShortCode(String shortCode);
    Optional<URL> findByOriginalUrl(String originalUrl);

    @Modifying
    @Transactional
    @Query("UPDATE URL u SET u.clickCount = u.clickCount + 1 WHERE u.shortCode = :shortCode")
    void incrementClickCount(@Param("shortCode") String shortCode);

}
