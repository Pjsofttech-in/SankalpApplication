package com.sankalpapp.dto.mapper;

import com.sankalpapp.dto.Request.CoordinatorRequest;
import com.sankalpapp.dto.Response.CoordinatorDTO;
import com.sankalpapp.entity.Center;
import com.sankalpapp.entity.Coordinator;
import com.sankalpapp.entity.User;

public class CoordinatorMapper {

    private CoordinatorMapper() {
    }

    public static CoordinatorDTO toDTO(Coordinator coordinator) {

        if (coordinator == null) {
            return null;
        }

        return CoordinatorDTO.builder()
                .id(coordinator.getId())
                .fullName(coordinator.getFullName())
                .email(coordinator.getEmail())
                .mobile(coordinator.getMobile())
                .address(coordinator.getAddress())
                .active(coordinator.getActive())

                .userId(
                        coordinator.getUser() != null
                                ? coordinator.getUser().getId()
                                : null
                )

                .centerId(
                        coordinator.getCenter() != null
                                ? coordinator.getCenter().getId()
                                : null
                )

                .centerName(
                        coordinator.getCenter() != null
                                ? coordinator.getCenter().getCenterName()
                                : null
                )

                .createdAt(coordinator.getCreatedAt())
                .updatedAt(coordinator.getUpdatedAt())
                .build();
    }

    public static Coordinator toEntity(
            CoordinatorRequest request,
            User user,
            Center center
    ) {

        return Coordinator.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .address(request.getAddress())
                .active(
                        request.getActive() == null || request.getActive()
                )
                .user(user)
                .center(center)
                .build();
    }
}