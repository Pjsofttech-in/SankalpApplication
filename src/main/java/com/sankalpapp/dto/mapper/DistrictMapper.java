package com.sankalpapp.dto.mapper;

import com.sankalpapp.dto.Request.DistrictRequest;
import com.sankalpapp.dto.Response.DistrictDTO;
import com.sankalpapp.entity.District;

import java.util.stream.Collectors;

public class DistrictMapper {

    private DistrictMapper() {
    }

    public static DistrictDTO toDTO(District district) {

        if (district == null) {
            return null;
        }

        return DistrictDTO.builder()
                .id(district.getId())
                .districtName(district.getDistrictName())
                .active(district.getActive())

                .talukaList(
                        district.getTalukas() == null
                                ? null
                                : district.getTalukas()
                                    .stream()
                                    .map(TalukaMapper::toDTO)
                                    .collect(Collectors.toList())
                )

                .createdAt(district.getCreatedAt())
                .updatedAt(district.getUpdatedAt())
                .build();
    }

    public static District toEntity(DistrictRequest request) {

        return District.builder()
                .districtName(request.getDistrictName())
                .active(
                        request.getActive() == null || request.getActive()
                )
                .build();
    }
}