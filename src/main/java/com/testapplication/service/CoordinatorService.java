package com.testapplication.service;

import com.testapplication.dto.Request.CoordinatorRequest;
import com.testapplication.dto.Response.CoordinatorResponse;

import java.util.List;

public interface CoordinatorService {

    CoordinatorResponse saveCoordinator(CoordinatorRequest request);

    CoordinatorResponse updateCoordinator(Long id, CoordinatorRequest request);

    void deleteCoordinator(Long id);

    CoordinatorResponse getCoordinatorById(Long id);

    List<CoordinatorResponse> getAllCoordinators();
}