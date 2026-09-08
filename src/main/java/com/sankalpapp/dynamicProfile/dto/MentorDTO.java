package com.sankalpapp.dynamicProfile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentorDTO {
    private Long id;

    private String mentorName;

    private String position;

    private String description;

    private String image;

    private Boolean active;
}