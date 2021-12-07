package com.example.imageservice.repository;

import com.example.imageservice.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("SELECT i FROM Image i LEFT JOIN i.tags t WHERE i.name LIKE %:parameter%" +
            " OR i.contentType LIKE %:parameter% OR i.reference LIKE %:parameter% " +
            " OR t.name LIKE %:parameter%")
    Page<Image> findAllByParameter(String parameter, Pageable pageable);
}
