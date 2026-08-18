package com.sankalpapp.dto.Response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoordinatorDTO {

    private Long id;

    private String fullName;

    private String email;

    private String mobile;

    private String address;

    private Boolean active;

    private Long userId;

    private Long centerId;

    private String centerName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}