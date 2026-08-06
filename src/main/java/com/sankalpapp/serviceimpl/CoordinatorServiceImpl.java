package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Request.CoordinatorRequest;
import com.sankalpapp.dto.Response.CoordinatorResponse;
import com.sankalpapp.entity.Coordinator;
import com.sankalpapp.entity.School;
import com.sankalpapp.entity.User;
import com.sankalpapp.repository.CoordinatorRepository;
import com.sankalpapp.repository.SchoolRepository;
import com.sankalpapp.repository.UserRepository;
import com.sankalpapp.service.CoordinatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoordinatorServiceImpl implements CoordinatorService {

    private final CoordinatorRepository coordinatorRepository;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    @Override
    public CoordinatorResponse saveCoordinator(CoordinatorRequest request) {

        if (coordinatorRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Coordinator email already exists.");
        }

        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new RuntimeException("School not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Coordinator coordinator = Coordinator.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .address(request.getAddress())
                .active(true)
                .school(school)
                .user(user)
                .build();

        return mapToResponse(coordinatorRepository.save(coordinator));
    }

    @Override
    public CoordinatorResponse updateCoordinator(Long id, CoordinatorRequest request) {

        Coordinator coordinator = coordinatorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Coordinator not found with id : " + id));

        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new RuntimeException("School not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        coordinator.setFullName(request.getFullName());
        coordinator.setEmail(request.getEmail());
        coordinator.setMobile(request.getMobile());
        coordinator.setAddress(request.getAddress());
        coordinator.setSchool(school);
        coordinator.setUser(user);

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
    public CoordinatorResponse getCoordinatorById(Long id) {

        Coordinator coordinator = coordinatorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Coordinator not found with id : " + id));

        return mapToResponse(coordinator);
    }

    @Override
    public List<CoordinatorResponse> getAllCoordinators() {

        return coordinatorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CoordinatorResponse mapToResponse(Coordinator coordinator) {

        return CoordinatorResponse.builder()
                .id(coordinator.getId())
                .fullName(coordinator.getFullName())
                .email(coordinator.getEmail())
                .mobile(coordinator.getMobile())
                .address(coordinator.getAddress())
                .active(coordinator.getActive())
                .schoolName(coordinator.getSchool().getSchoolName())
                .build();
    }
}