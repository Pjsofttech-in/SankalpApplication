package com.sankalpapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VUserDTO {

    private Long id;
    private String userName;
    private String email;
    private String password;
    private String confirmPassword;
    private Long contact;
    private String examName;
    private LocalDate createdDate;
    private String district;
    // private double paidAmount;

    private List<AddressDTO> addresses;

}
