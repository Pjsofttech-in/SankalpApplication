package com.sankalpapp.serviceimpl;

import com.sankalpapp.entity.School;
import com.sankalpapp.repository.SchoolRepository;
import com.sankalpapp.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    @Override
    public School saveSchool(School school) {

        if (schoolRepository.existsByEmail(school.getEmail())) {
            throw new RuntimeException("School email already exists.");
        }

        return schoolRepository.save(school);
    }

    @Override
    public School updateSchool(Long id, School school) {

        School existingSchool = getSchoolById(id);

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

        return schoolRepository.save(existingSchool);
    }

    @Override
    public void deleteSchool(Long id) {

        School school = getSchoolById(id);
        schoolRepository.delete(school);
    }

    @Override
    public School getSchoolById(Long id) {

        return schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found with id : " + id));
    }

    @Override
    public List<School> getAllSchools() {

        return schoolRepository.findAll();
    }
}