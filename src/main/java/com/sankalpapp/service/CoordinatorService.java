package com.sankalpapp.service;

import com.sankalpapp.dto.request.CoordinatorRequest;
import com.sankalpapp.dto.response.CoordinatorDTO;

import java.util.List;

public interface CoordinatorService {

    CoordinatorDTO saveCoordinator(CoordinatorRequest request);

    CoordinatorDTO updateCoordinator(Long id, CoordinatorRequest request);

    void deleteCoordinator(Long id);

    List<CoordinatorDTO> getCoordinatorByCenter(Long centerId);

    CoordinatorDTO getCoordinatorById(Long id);

    List<CoordinatorDTO> getAllCoordinators();
}