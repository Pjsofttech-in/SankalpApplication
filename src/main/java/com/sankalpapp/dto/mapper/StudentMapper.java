package com.sankalpapp.dto.mapper;

import com.sankalpapp.dto.response.StudentDTO;
import com.sankalpapp.entity.Center;
import com.sankalpapp.entity.Coordinator;
import com.sankalpapp.entity.Payment;
import com.sankalpapp.entity.Student;

import java.util.Optional;

public final class StudentMapper {

    private StudentMapper() {
    }

    public static StudentDTO toDTO(Student student) {

        if (student == null) {
            return null;
        }

        Center center = student.getCenter();
        Coordinator coordinator = student.getCoordinator();

        return StudentDTO.builder()
                .id(student.getId())
                .studentName(student.getStudentName())
                .fatherName(student.getFatherName())
                .lastName(student.getLastName())
                .examMode(student.getExamMode())
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

                .centerId(Optional.ofNullable(center).map(Center::getId).orElse(null))
                .centerName(Optional.ofNullable(center).map(Center::getCenterName).orElse(""))

                .coordinatorId(Optional.ofNullable(coordinator).map(Coordinator::getId).orElse(null))
                .coordinatorName(Optional.ofNullable(coordinator).map(Coordinator::getFullName).orElse(""))

                .isPaymentDone(student.getPayment() != null && Payment.PaymentStatus.SUCCESS.name().equalsIgnoreCase(student.getPayment().getPaymentStatus()))
                .paymentAmount(student.getPayment() != null && student.getPayment().getAmount() != null
                        ? student.getPayment().getAmount() : null)

                .build();
    }
}