package com.sankalpapp.controller;

import com.sankalpapp.dto.response.VMUserMaterialRequestDTO;
import com.sankalpapp.dto.response.VMUserMaterialResponseDTO;
import com.sankalpapp.entity.VMUserMaterialAssociation;
import com.sankalpapp.service.VMUserMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vmUserMaterial")
public class VMUserMaterialController {

    @Autowired
    private VMUserMaterialService vmUserMaterialService;

    @PostMapping("/addUserMaterialAssociation")
    public ResponseEntity<VMUserMaterialAssociation> addUserMaterialAssociation(
            @RequestBody VMUserMaterialRequestDTO vmUserMaterialRequestDTO) {

        VMUserMaterialAssociation vmUserMaterialAssociation = vmUserMaterialService.addUserMaterialAssociation(
                vmUserMaterialRequestDTO.getEmail(),
                vmUserMaterialRequestDTO.getMaterialId(),
                vmUserMaterialRequestDTO.getPaidAmount());

        return ResponseEntity.ok(vmUserMaterialAssociation);
    }

    @GetMapping("/UserAndMaterialsByUsernameAndEmail")
    public ResponseEntity<VMUserMaterialResponseDTO> getUserAndMaterialsByUsernameAndEmail(@RequestParam String email) {
        VMUserMaterialResponseDTO response = vmUserMaterialService.getUserAndMaterialsByUsernameAndEmail(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/AllUserMaterial")
    public ResponseEntity<List<VMUserMaterialAssociation>> getAllUserMaterial() {
        List<VMUserMaterialAssociation> response = vmUserMaterialService.getAllUserMaterialAssociations();
        return ResponseEntity.ok(response);
    }

}

