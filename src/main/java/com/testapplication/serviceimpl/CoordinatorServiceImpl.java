package com.testapplication.serviceimpl;

import com.testapplication.entity.Coordinator;
import com.testapplication.repository.CoordinatorRepository;
import com.testapplication.service.CoordinatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoordinatorServiceImpl implements CoordinatorService {

    private final CoordinatorRepository coordinatorRepository;

    @Override
    public Coordinator saveCoordinator(Coordinator coordinator) {

        if (coordinatorRepository.existsByEmail(coordinator.getEmail())) {
            throw new RuntimeException("Coordinator email already exists.");
        }

        return coordinatorRepository.save(coordinator);
    }

    @Override
    public Coordinator updateCoordinator(Long id, Coordinator coordinator) {

        Coordinator existingCoordinator = getCoordinatorById(id);

        existingCoordinator.setFullName(coordinator.getFullName());
        existingCoordinator.setEmail(coordinator.getEmail());
        existingCoordinator.setMobile(coordinator.getMobile());
        existingCoordinator.setAddress(coordinator.getAddress());
        existingCoordinator.setActive(coordinator.getActive());
        existingCoordinator.setUser(coordinator.getUser());
        existingCoordinator.setSchool(coordinator.getSchool());

        return coordinatorRepository.save(existingCoordinator);
    }

    @Override
    public void deleteCoordinator(Long id) {

        Coordinator coordinator = getCoordinatorById(id);
        coordinatorRepository.delete(coordinator);
    }

    @Override
    public Coordinator getCoordinatorById(Long id) {

        return coordinatorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordinator not found with id : " + id));
    }

    @Override
    public List<Coordinator> getAllCoordinators() {

        return coordinatorRepository.findAll();
    }
}