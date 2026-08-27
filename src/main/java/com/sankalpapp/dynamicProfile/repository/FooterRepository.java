package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebFooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FooterRepository extends JpaRepository<WebFooter, Long> {

    @Query("SELECT f FROM WebFooter f ORDER BY f.id DESC")
    List<WebFooter> findAllOrderById();

}