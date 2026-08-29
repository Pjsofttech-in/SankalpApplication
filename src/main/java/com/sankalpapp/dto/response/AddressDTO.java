package com.sankalpapp.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long id;
    private String address;
    private String state;
    private String district;
    private String area;
    private String city;
    private int pincode;
    private String landmark;
}

