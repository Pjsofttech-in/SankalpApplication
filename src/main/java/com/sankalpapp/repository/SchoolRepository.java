package com.sankalpapp.repository;

import com.sankalpapp.entity.School;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {

    Optional<School> findBySchoolName(String schoolName);

    List<School> findByCenter_CenterNameContainingIgnoreCaseOrCenter_Id(String centerName, Long centerId);

    boolean existsByEmail(String email);

}