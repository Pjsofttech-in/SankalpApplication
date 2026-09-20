package com.sankalpapp.repository;

import com.sankalpapp.entity.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {

    Optional<Coordinator> findByEmail(String email);

    /**
     * Finds a single active Coordinator associated with a specific Center ID.
     * Use this if you have a strict 1-to-1 rule (only one active coordinator per center at a time).
     */
    Optional<Coordinator> findByCenterIdAndActiveTrue(Long centerId);

    /**
     * Alternative: Finds a list of active Coordinators associated with a specific Center ID.
     * Use this if your business logic allows multiple active coordinators per center.
     */
    List<Coordinator> findAllByCenterIdAndActiveTrue(Long centerId);

    boolean existsByEmail(String email);

}