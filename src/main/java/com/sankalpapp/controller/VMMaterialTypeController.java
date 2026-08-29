package com.sankalpapp.controller;

import com.sankalpapp.entity.VMMaterialType;
import com.sankalpapp.service.VMMaterialTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://vartmannirnay.com")
public class VMMaterialTypeController {

    @Autowired
    private VMMaterialTypeService vmMaterialTypeService;

    @PostMapping("/createVMMaterialType")
    public ResponseEntity<VMMaterialType> createMaterialType(@RequestBody VMMaterialType vmMaterialType) {
        VMMaterialType createdMaterialType = vmMaterialTypeService.createMaterialType(vmMaterialType);
        return ResponseEntity.ok(createdMaterialType);
    }

    @GetMapping("/VMMaterialTypeById/{id}")
    public ResponseEntity<VMMaterialType> getMaterialTypeById(@PathVariable Long id) {
        Optional<VMMaterialType> vmMaterialType = vmMaterialTypeService.getMaterialTypeById(id);
        return vmMaterialType.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/AllVMMaterialTypes")
    public ResponseEntity<List<VMMaterialType>> getAllMaterialTypes() {
        List<VMMaterialType> vmMaterialTypes = vmMaterialTypeService.getAllMaterialTypes();
        return ResponseEntity.ok(vmMaterialTypes);
    }

    @PutMapping("/updateVMMaterialType/{id}")
    public ResponseEntity<VMMaterialType> updateMaterialType(@PathVariable Long id, @RequestBody VMMaterialType vmMaterialType) {
        VMMaterialType updatedMaterialType = vmMaterialTypeService.updateMaterialType(id, vmMaterialType);
        if (updatedMaterialType != null) {
            return ResponseEntity.ok(updatedMaterialType);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteVMMaterialType/{id}")
    public ResponseEntity<Void> deleteMaterialType(@PathVariable Long id) {
        vmMaterialTypeService.deleteMaterialType(id);
        return ResponseEntity.noContent().build();
    }

}


