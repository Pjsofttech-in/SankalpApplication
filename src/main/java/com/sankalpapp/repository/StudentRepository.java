package com.sankalpapp.repository;

import com.sankalpapp.entity.Student;
import com.sankalpapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
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

    Optional<Student> findByUser(User user);

    Optional<Student> findByUserId(Long userId);

    @Query(value = """
        SELECT DATE(created_at) AS period,
               COUNT(*) AS count
        FROM students
        WHERE MONTH(created_at) = :month
          AND YEAR(created_at) = :year
        GROUP BY DATE(created_at)
        ORDER BY DATE(created_at)
        """, nativeQuery = true)
    List<Object[]> getStudentsDayWise(
            @Param("year") int year,
            @Param("month") int month
    );

    @Query(value = """
            SELECT DATE_FORMAT(created_at, '%b') AS period,
                           COUNT(*) AS count
                    FROM students
                    WHERE YEAR(created_at) = :year
                    GROUP BY MONTH(created_at)
                    ORDER BY MONTH(created_at);
            """, nativeQuery = true)
    List<Object[]> getStudentsMonthWise(
            @Param("year") int year
    );

    @Query(value = """
            SELECT YEAR(created_at) AS period,
                   COUNT(*) AS count
            FROM students
            WHERE YEAR(created_at) BETWEEN :year1 AND :year2
            GROUP BY YEAR(created_at)
            ORDER BY YEAR(created_at)
            """, nativeQuery = true)
    List<Object[]> getStudentsYearWise(
            @Param("year1") int year1,
            @Param("year2") int year2
    );

}