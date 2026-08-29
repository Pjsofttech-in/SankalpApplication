package com.sankalpapp.dto.response;

import com.sankalpapp.entity.User;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class VMUserMaterialResponseDTO {
    private User vUser;
    private double paidAmount;
    private List<VMMaterialDTO> materials;

    public VMUserMaterialResponseDTO(User vUser, List<VMMaterialDTO> materials) {
        this.vUser = vUser;
        this.materials = materials;
    }


}