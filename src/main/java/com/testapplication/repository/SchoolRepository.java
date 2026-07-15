package com.testapplication.repository;

import com.testapplication.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {

    Optional<School> findBySchoolName(String schoolName);

    boolean existsByEmail(String email);

}