package com.sankalpapp.dto.mapper;

import com.sankalpapp.dto.response.StudentDTO;
import com.sankalpapp.entity.Payment;
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
                .fatherName(student.getFatherName())
                .lastName(student.getLastName())
                .mobile(student.getMobile())
                .email(student.getEmail())
                .gender(student.getGender())
                .studentClass(student.getStudentClass())
                .medium(student.getMedium())
                .address(student.getAddress())
                .village(student.getVillage())
                .state(student.getState())
                .pincode(student.getPincode())
                .dateOfBirth(student.getDateOfBirth())
                .active(student.getActive())

                .school(student.getSchool())

                .districtId(student.getDistrict().getId())
                .districtName(student.getDistrict().getDistrictName())

                .talukaId(student.getTaluka().getId())
                .talukaName(student.getTaluka().getTalukaName())

                .centerId(student.getCenter().getId())
                .centerName(student.getCenter().getCenterName())

                .coordinatorId(student.getCoordinator().getId())
                .coordinatorName(student.getCoordinator().getFullName())

                .isPaymentDone(student.getPayment() != null && Payment.PaymentStatus.SUCCESS.name().equalsIgnoreCase(student.getPayment().getPaymentStatus()))

                .build();
    }
}