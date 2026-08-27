package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebContactForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactFormRepository extends JpaRepository<WebContactForm, Long> {

    @Query("SELECT c FROM WebContactForm c ORDER BY c.id DESC")
    List<WebContactForm> findAllOrderById();

}