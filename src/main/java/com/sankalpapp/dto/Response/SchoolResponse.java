package com.sankalpapp.dto.Response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolResponse {

    private Long id;
    private String schoolName;
    private String principalName;
    private String email;
    private String mobile;
    private String address;
    private String village;
    private String taluka;
    private String district;
    private String state;
    private String pincode;
    private Boolean active;
    
    // Flattened Relationship Fields
    private Long userId;
    private String userEmail; // Optional helper field
    
    private Long centerId;
    private String centerName; // Optional helper field

    // Counter helpers instead of pulling heavy nested lists
    private int totalCoordinators;
    private int totalStudents;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
