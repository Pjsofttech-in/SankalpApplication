package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.ExamSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamSectionRepository extends JpaRepository<ExamSection, Long> {
}