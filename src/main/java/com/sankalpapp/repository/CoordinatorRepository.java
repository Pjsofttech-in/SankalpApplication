package com.sankalpapp.repository;

import com.sankalpapp.entity.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {

    Optional<Coordinator> findByEmail(String email);

    boolean existsByEmail(String email);

}