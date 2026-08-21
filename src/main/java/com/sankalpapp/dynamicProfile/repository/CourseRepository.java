package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<WebCourse, Integer> {

    @Query("SELECT c FROM WebCourse c WHERE c.branchCode = :branchCode ORDER BY c.id DESC")
    List<WebCourse> findAllByBranchCode(String branchCode);
}