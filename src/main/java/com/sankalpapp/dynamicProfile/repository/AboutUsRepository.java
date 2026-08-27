package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebAboutUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AboutUsRepository extends JpaRepository<WebAboutUs, Integer> {

    @Query("SELECT a FROM WebAboutUs a ORDER BY a.id DESC")
    List<WebAboutUs> findAllOrderById();

}