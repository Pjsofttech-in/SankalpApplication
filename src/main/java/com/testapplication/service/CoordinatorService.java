package com.testapplication.service;

import com.testapplication.entity.Coordinator;

import java.util.List;

public interface CoordinatorService {

    Coordinator saveCoordinator(Coordinator coordinator);

    Coordinator updateCoordinator(Long id, Coordinator coordinator);

    void deleteCoordinator(Long id);

    Coordinator getCoordinatorById(Long id);

    List<Coordinator> getAllCoordinators();
}