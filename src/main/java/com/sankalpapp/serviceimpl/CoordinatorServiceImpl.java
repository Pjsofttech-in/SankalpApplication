package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Request.CoordinatorRequest;
import com.sankalpapp.dto.Response.CoordinatorDTO;
import com.sankalpapp.entity.Center;
import com.sankalpapp.entity.Coordinator;
import com.sankalpapp.entity.User;
import com.sankalpapp.repository.CenterRepository;
import com.sankalpapp.repository.CoordinatorRepository;
import com.sankalpapp.repository.UserRepository;
import com.sankalpapp.service.CoordinatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoordinatorServiceImpl implements CoordinatorService {

    private final CoordinatorRepository coordinatorRepository;
    private final UserRepository userRepository;
    private final CenterRepository centerRepository;

    @Override
    public CoordinatorDTO saveCoordinator(CoordinatorRequest request) {

        if (coordinatorRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Coordinator email already exists.");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Center center = centerRepository.findById(request.getCenterId())
                .orElseThrow(() -> new RuntimeException("Center not found"));

        Coordinator coordinator = Coordinator.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .address(request.getAddress())
                .active(true)
                .user(user)
                .center(center)
                .build();

        return mapToResponse(coordinatorRepository.save(coordinator));
    }

    @Override
    public CoordinatorDTO updateCoordinator(Long id, CoordinatorRequest request) {

        Coordinator coordinator = coordinatorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Coordinator not found with id : " + id));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Center center = centerRepository.findById(request.getCenterId())
                .orElseThrow(() -> new RuntimeException("Center not found"));

        coordinator.setFullName(request.getFullName());
        coordinator.setEmail(request.getEmail());
        coordinator.setMobile(request.getMobile());
        coordinator.setAddress(request.getAddress());
        coordinator.setUser(user);
        coordinator.setCenter(center);

        return mapToResponse(coordinatorRepository.save(coordinator));
    }

    @Override
    public void deleteCoordinator(Long id) {

        Coordinator coordinator = coordinatorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Coordinator not found with id : " + id));

        coordinatorRepository.delete(coordinator);
    }

    @Override
    public List<CoordinatorDTO> getCoordinatorByCenter(Long centerId) {
        List<Coordinator> coordinatorList = coordinatorRepository.findAllByCenterIdAndActiveTrue(centerId);
        if (!CollectionUtils.isEmpty(coordinatorList)) {
            return coordinatorList.stream().map(this::mapToResponse).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public CoordinatorDTO getCoordinatorById(Long id) {
        Coordinator coordinator = coordinatorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Coordinator not found with id : " + id));

        return mapToResponse(coordinator);
    }

    @Override
    public List<CoordinatorDTO> getAllCoordinators() {

        return coordinatorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CoordinatorDTO mapToResponse(Coordinator coordinator) {

        return CoordinatorDTO.builder()
                .id(coordinator.getId())
                .fullName(coordinator.getFullName())
                .email(coordinator.getEmail())
                .mobile(coordinator.getMobile())
                .address(coordinator.getAddress())
                .active(coordinator.getActive())
                .centerName(coordinator.getCenter().getCenterName())
                .centerId(coordinator.getCenter().getId())
                .userId(coordinator.getUser().getId())
                .userId(coordinator.getUser().getId())
                .build();
    }
}