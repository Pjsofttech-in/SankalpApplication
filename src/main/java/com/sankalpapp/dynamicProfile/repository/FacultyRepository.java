package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebFaculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<WebFaculty, Long> {

    @Query("SELECT f FROM WebFaculty f ORDER BY f.id DESC")
    List<WebFaculty> findAllOrderById();
}