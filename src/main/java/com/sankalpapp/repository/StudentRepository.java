package com.sankalpapp.repository;

import com.sankalpapp.entity.Student;
import com.sankalpapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>,
        JpaSpecificationExecutor<Student> {

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    Optional<Student> findByUserId(Long userId);

}