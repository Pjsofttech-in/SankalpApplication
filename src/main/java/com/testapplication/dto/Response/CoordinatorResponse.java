package com.testapplication.dto.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoordinatorResponse {

    private Long id;

    private String fullName;
    private String email;
    private String mobile;
    private String address;

    private Boolean active;

    private Long schoolId;
    private String schoolName;

    private Long userId;
    private String userName;
}