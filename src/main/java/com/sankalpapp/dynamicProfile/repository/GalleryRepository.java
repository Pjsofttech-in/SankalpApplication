package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GalleryRepository extends JpaRepository<WebGallery, Long> {
    @Query("SELECT g FROM WebGallery g ORDER BY g.galleryId DESC")
    List<WebGallery> findAllOrderById();
}