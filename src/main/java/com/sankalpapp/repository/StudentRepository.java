package com.sankalpapp.repository;

import com.sankalpapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>,
        JpaSpecificationExecutor<Student> {

    Optional<Student> findByEmail(String email);

    Optional<Student> findByMobile(String mobile);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    @Query("""
                SELECT DISTINCT s.school
                FROM Student s
                WHERE s.taluka.id = (
                    SELECT c.taluka.id
                    FROM Center c
                    WHERE c.id = :centerId
                )
                ORDER BY s.school
            """)
    List<String> findUniqueSchoolsByCenterTaluka(
            @Param("centerId") Long centerId
    );

}