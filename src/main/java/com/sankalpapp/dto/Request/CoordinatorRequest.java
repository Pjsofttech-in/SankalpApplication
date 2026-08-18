package com.sankalpapp.dto.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoordinatorRequest {

    private String fullName;

    private String password;

    private String email;

    private String mobile;

    private String address;

    private Boolean active;

    private Long userId;

    private Long centerId;
}