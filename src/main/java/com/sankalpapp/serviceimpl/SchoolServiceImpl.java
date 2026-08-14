package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Response.SchoolResponse;
import com.sankalpapp.entity.School;
import com.sankalpapp.repository.SchoolRepository;
import com.sankalpapp.service.SchoolService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final EntityManager entityManager;

    @Override
    public SchoolResponse saveSchool(School school) {

        if (schoolRepository.existsByEmail(school.getEmail())) {
            throw new RuntimeException("School email already exists.");
        }
        schoolRepository.saveAndFlush(school);
        // 2. Evict the school instance from Hibernate's local memory cache
        entityManager.clear();

        return getSchoolById(school.getId());
    }

    @Override
    public SchoolResponse updateSchool(Long id, School school) {

        School existingSchool = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found with id : " + id));

        existingSchool.setSchoolName(school.getSchoolName());
        existingSchool.setPrincipalName(school.getPrincipalName());
        existingSchool.setEmail(school.getEmail());
        existingSchool.setMobile(school.getMobile());
        existingSchool.setAddress(school.getAddress());
        existingSchool.setVillage(school.getVillage());
        existingSchool.setTaluka(school.getTaluka());
        existingSchool.setDistrict(school.getDistrict());
        existingSchool.setState(school.getState());
        existingSchool.setPincode(school.getPincode());
        existingSchool.setActive(school.getActive());
        existingSchool.setUser(school.getUser());

        return mapToSchoolResponse(schoolRepository.save(existingSchool));
    }

    @Override
    public void deleteSchool(Long id) {

        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found with id : " + id));
        schoolRepository.delete(school);
    }

    @Override
    public SchoolResponse getSchoolById(Long id) {
        return mapToSchoolResponse(schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found with id : " + id)));
    }

    @Override
    public List<SchoolResponse> getSchoolByCenterIdOrName(Long id, String centerName) {
        return schoolRepository.findByCenter_CenterNameContainingIgnoreCaseOrCenter_Id(centerName, id).stream().map(this::mapToSchoolResponse).toList();
    }

    @Override
    public List<SchoolResponse> getAllSchools() {

        return schoolRepository.findAll().stream().map(this::mapToSchoolResponse).toList();
    }

    public SchoolResponse mapToSchoolResponse(School school) {
        if (school == null) {
            return null;
        }

        SchoolResponse.SchoolResponseBuilder builder = SchoolResponse.builder()
                .id(school.getId())
                .schoolName(school.getSchoolName())
                .principalName(school.getPrincipalName())
                .email(school.getEmail())
                .mobile(school.getMobile())
                .address(school.getAddress())
                .village(school.getVillage())
                .taluka(school.getTaluka())
                .district(school.getDistrict())
                .state(school.getState())
                .pincode(school.getPincode())
                .active(school.getActive())
                .createdAt(school.getCreatedAt())
                .updatedAt(school.getUpdatedAt());

        // Null-safe extraction for Lazy User relationship
        if (school.getUser() != null) {
            builder.userId(school.getUser().getId());
            // Uncomment if your User entity has an email field
             builder.userEmail(school.getUser().getEmail());
        }

        // Null-safe extraction for Lazy Center relationship
        if (school.getCenter() != null) {
            builder.centerId(school.getCenter().getId());
            // Uncomment if your Center entity has a centerName field
             builder.centerName(school.getCenter().getCenterName());
        }

        // Safe size tracking for collection fields
        builder.totalCoordinators(school.getCoordinators() != null ? school.getCoordinators().size() : 0);
        builder.totalStudents(school.getStudents() != null ? school.getStudents().size() : 0);

        return builder.build();
    }

}