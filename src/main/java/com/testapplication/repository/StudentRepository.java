package com.testapplication.repository;

import com.testapplication.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    Optional<Student> findByMobile(String mobile);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

}