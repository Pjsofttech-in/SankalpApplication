package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebMapAndImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MapAndImagesRepository extends JpaRepository<WebMapAndImages, Long> {

    @Query("SELECT m FROM WebMapAndImages m ORDER BY m.id DESC")
    List<WebMapAndImages> findAllOrderById();

    
}