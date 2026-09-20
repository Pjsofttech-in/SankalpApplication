package com.sankalpapp.dto.mapper;

import com.sankalpapp.dto.request.CenterRequest;
import com.sankalpapp.dto.response.CenterDTO;
import com.sankalpapp.entity.Center;
import com.sankalpapp.entity.Coordinator;
import com.sankalpapp.entity.District;
import com.sankalpapp.entity.Taluka;

public class CenterMapper {

    private CenterMapper() {
    }

    public static CenterDTO toDTO(Center center) {

        if (center == null) {
            return null;
        }

        return CenterDTO.builder()
                .id(center.getId())
                .centerName(center.getCenterName())
                .centerCode(center.getCenterCode())
                .address(center.getAddress())
                .village(center.getVillage())
                .state(center.getState())
                .pincode(center.getPincode())
                .active(center.getActive())

                .districtId(
                        center.getDistrict() != null
                                ? center.getDistrict().getId()
                                : null
                )

                .districtName(
                        center.getDistrict() != null
                                ? center.getDistrict().getDistrictName()
                                : null
                )

                .talukaId(
                        center.getTaluka() != null
                                ? center.getTaluka().getId()
                                : null
                )

                .talukaName(
                        center.getTaluka() != null
                                ? center.getTaluka().getTalukaName()
                                : null
                )

                .coordinatorId(
                        center.getCoordinator() != null
                                ? center.getCoordinator().getId()
                                : null
                )

                .coordinatorName(
                        center.getCoordinator() != null
                                ? center.getCoordinator().getFullName()
                                : null
                )

                .createdAt(center.getCreatedAt())
                .updatedAt(center.getUpdatedAt())
                .build();
    }

    public static Center toEntity(
            CenterRequest request,
            District district,
            Taluka taluka,
            Coordinator coordinator
    ) {

        return Center.builder()
                .centerName(request.getCenterName())
                .centerCode(request.getCenterCode())
                .address(request.getAddress())
                .village(request.getVillage())
                .state(request.getState())
                .pincode(request.getPincode())
                .active(
                        request.getActive() == null || request.getActive()
                )
                .district(district)
                .taluka(taluka)
                .coordinator(coordinator)
                .build();
    }
}