package com.sankalpapp.dto.mapper;

import com.sankalpapp.dto.Request.TalukaRequest;
import com.sankalpapp.dto.Response.TalukaDTO;
import com.sankalpapp.entity.District;
import com.sankalpapp.entity.Taluka;

import java.util.stream.Collectors;

public class TalukaMapper {

    private TalukaMapper() {
    }

    public static TalukaDTO toDTO(Taluka taluka) {

        if (taluka == null) {
            return null;
        }

        return TalukaDTO.builder()
                .id(taluka.getId())
                .talukaName(taluka.getTalukaName())

                .districtId(
                        taluka.getDistrict() != null
                                ? taluka.getDistrict().getId()
                                : null
                )

                .districtName(
                        taluka.getDistrict() != null
                                ? taluka.getDistrict().getDistrictName()
                                : null
                )

                .active(taluka.getActive())

                .centerIds(
                        taluka.getCenters() == null
                                ? null
                                : taluka.getCenters()
                                    .stream()
                                    .map(center -> center.getId())
                                    .collect(Collectors.toList())
                )

                .createdAt(taluka.getCreatedAt())
                .updatedAt(taluka.getUpdatedAt())
                .build();
    }

    public static Taluka toEntity(
            TalukaRequest request,
            District district
    ) {

        return Taluka.builder()
                .talukaName(request.getTalukaName())
                .district(district)
                .active(
                        request.getActive() == null || request.getActive()
                )
                .build();
    }
}