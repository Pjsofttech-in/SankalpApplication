package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebFacultyTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyTitleRepository extends JpaRepository<WebFacultyTitle, Long> {
    @Query("SELECT f FROM WebFacultyTitle f ORDER BY f.id DESC")
    List<WebFacultyTitle> findAllOrderById();
}
