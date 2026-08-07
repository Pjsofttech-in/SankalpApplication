package com.sankalpapp.service;

import com.sankalpapp.dto.Request.CoordinatorRequest;
import com.sankalpapp.dto.Response.CoordinatorResponse;

import java.util.List;

public interface CoordinatorService {

    CoordinatorResponse saveCoordinator(CoordinatorRequest request);

    CoordinatorResponse updateCoordinator(Long id, CoordinatorRequest request);

    void deleteCoordinator(Long id);

    List<CoordinatorResponse> getCoordinatorByCenter(Long centerId);

    CoordinatorResponse getCoordinatorById(Long id);

    List<CoordinatorResponse> getAllCoordinators();
}