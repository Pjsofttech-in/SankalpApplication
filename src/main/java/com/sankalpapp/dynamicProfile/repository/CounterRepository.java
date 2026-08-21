package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounterRepository extends JpaRepository<WebCounter, Long> {

    @Query("SELECT c FROM WebCounter c ORDER BY c.id DESC")
    List<WebCounter> findAllOrderById();
}