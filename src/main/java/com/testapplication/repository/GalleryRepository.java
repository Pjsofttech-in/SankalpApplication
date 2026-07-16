package com.testapplication.repository;

import com.testapplication.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {

    List<Gallery> findByActiveTrueOrderByDisplayOrderAsc();

    List<Gallery> findByCategoryIgnoreCase(String category);
}