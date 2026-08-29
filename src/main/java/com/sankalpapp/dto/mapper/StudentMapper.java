package com.sankalpapp.dto.mapper;

import com.sankalpapp.dto.response.StudentDTO;
import com.sankalpapp.entity.Student;

public final class StudentMapper {

    private StudentMapper() {
    }

    public static StudentDTO toDTO(Student student) {

        if (student == null) {
            return null;
        }

        return StudentDTO.builder()

                .id(student.getId())

                .studentName(student.getStudentName())
                .mobile(student.getMobile())
                .email(student.getEmail())
                .gender(student.getGender())
                .studentClass(student.getStudentClass())
                .medium(student.getMedium())

                .address(student.getAddress())
                .village(student.getVillage())
                .state(student.getState())
                .pincode(student.getPincode())

                .school(student.getSchool())
                .dateOfBirth(student.getDateOfBirth())
                .active(student.getActive())

                .districtId(
                        student.getDistrict() != null
                                ? student.getDistrict().getId()
                                : null
                )

                .districtName(
                        student.getDistrict() != null
                                ? student.getDistrict().getDistrictName()
                                : null
                )

                .talukaId(
                        student.getTaluka() != null
                                ? student.getTaluka().getId()
                                : null
                )

                .talukaName(
                        student.getTaluka() != null
                                ? student.getTaluka().getTalukaName()
                                : null
                )

                .centerId(
                        student.getCenter() != null
                                ? student.getCenter().getId()
                                : null
                )

                .centerName(
                        student.getCenter() != null
                                ? student.getCenter().getCenterName()
                                : null
                )

                .coordinatorId(
                        student.getCoordinator() != null
                                ? student.getCoordinator().getId()
                                : null
                )

                .coordinatorName(
                        student.getCoordinator() != null
                                ? student.getCoordinator().getFullName()
                                : null
                )

                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())

                .build();
    }
}