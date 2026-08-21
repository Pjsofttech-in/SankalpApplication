package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebTopper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopperRepository extends JpaRepository<WebTopper, Long> {
    @Query("SELECT t FROM WebTopper t ORDER BY t.topperId DESC")
    List<WebTopper> findAllOrderById();
}
