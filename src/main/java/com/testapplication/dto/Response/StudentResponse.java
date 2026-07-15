package com.testapplication.dto.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {

    private Long id;
    private String studentName;
    private String email;
    private String mobile;
    private String gender;
    private String studentClass;
    private String medium;
    private String schoolName;
    private String centerName;
    private String coordinatorName;
    private Boolean active;
}