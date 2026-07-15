package com.testapplication.repository;

import com.testapplication.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CenterRepository extends JpaRepository<Center, Long> {

    Optional<Center> findByCenterCode(String centerCode);

}