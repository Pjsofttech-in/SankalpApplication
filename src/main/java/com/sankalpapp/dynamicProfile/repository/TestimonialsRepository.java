package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebTestimonials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestimonialsRepository extends JpaRepository<WebTestimonials, Long> {

    @Query("SELECT t FROM WebTestimonials t WHERE t.branchCode = :branchCode ORDER BY t.testimonialId DESC")
    List<WebTestimonials> findAllByBranchCode(String branchCode);
}