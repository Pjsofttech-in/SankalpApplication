package com.sankalpapp.controller;

import com.sankalpapp.entity.VMMaterial;
import com.sankalpapp.service.VMMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/vmMaterial")
public class VMMaterialController {

    @Autowired
    private VMMaterialService vmMaterialService;

    @PostMapping("/createVMMaterial")
    public ResponseEntity<VMMaterial> createMaterial(@RequestParam("materialtype") String materialType,
                                                     // @RequestParam("materialName") String materialName,
                                                     @RequestParam("saveToDevice") Boolean saveToDevice,
                                                     @RequestParam("status") String status,
                                                     @RequestParam("mrp") Double mrp,
                                                     @RequestParam("price") Double price,
                                                     @RequestParam("validity") Integer validity,
                                                     @RequestParam("chapterName") String chapterName,
                                                     @RequestParam(value = "seo", required = false) String seo,
                                                     @RequestParam("discription") String discription,
                                                     @RequestParam("subcategory.id") Long subcategoryId,
                                                     @RequestPart(value = "demoPdf") MultipartFile demoPdf,
                                                     @RequestPart(value = "pdfFile") MultipartFile pdfFile,
                                                     @RequestPart(value = "thumbnailFile") MultipartFile thumbnailFile) {
        VMMaterial savedMaterial = vmMaterialService.addMaterial(materialType, saveToDevice, status, mrp, price, validity, chapterName,
                seo, discription, subcategoryId, demoPdf, pdfFile, thumbnailFile);
        return new ResponseEntity<>(savedMaterial, HttpStatus.CREATED);
    }


    @PutMapping("/updateVMMaterial/{materialId}")
    public ResponseEntity<VMMaterial> updateMaterial(@PathVariable Long materialId,
                                                     @RequestParam("materialtype") String materialType,
                                                     @RequestParam("saveToDevice") Boolean saveToDevice,
                                                     @RequestParam("status") String status,
                                                     @RequestParam(value = "mrp", required = false, defaultValue = "0.0") Double mrp,
                                                     @RequestParam(value = "price", required = false, defaultValue = "0.0") Double price,
                                                     @RequestParam(value = "validity", required = false) Integer validity,
                                                     @RequestParam("chapterName") String chapterName,
                                                     @RequestParam(value = "seo", required = false) String seo,
                                                     @RequestParam("discription") String discription,
                                                     @RequestParam("subcategoryName") String subcategoryName,
                                                     @RequestPart(value = "demoPdf", required = false) MultipartFile demoPdf,
                                                     @RequestPart(value = "pdfFile", required = false) MultipartFile pdfFile,
                                                     @RequestPart(value = "thumbnailFile", required = false) MultipartFile thumbnailFile) {

        if ("free".equalsIgnoreCase(status)) {
            mrp = 0.0;
            price = 0.0;
        }
        VMMaterial updatedMaterial = vmMaterialService.updateMaterial(materialId, materialType, saveToDevice,
                status, mrp, price, validity, chapterName, seo, discription, subcategoryName, demoPdf, pdfFile, thumbnailFile);
        return new ResponseEntity<>(updatedMaterial, HttpStatus.OK);
    }


    @GetMapping("/VMMaterialById/{id}")
    public ResponseEntity<VMMaterial> getMaterialById(@PathVariable Long id) {
        VMMaterial vmMaterial = vmMaterialService.getMaterialById(id);
        return new ResponseEntity<>(vmMaterial, HttpStatus.OK);
    }

    @GetMapping("/AllVMMaterials")
    public ResponseEntity<List<VMMaterial>> getAllMaterials() {
        List<VMMaterial> vmMaterials = vmMaterialService.getAllMaterials();
        return new ResponseEntity<>(vmMaterials, HttpStatus.OK);
    }

    @DeleteMapping("/deleteVMMaterial/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        vmMaterialService.deleteMaterial(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/toggledownload/{id}")
    public ResponseEntity<VMMaterial> toggleDownload(@PathVariable Long id) {
        VMMaterial updatedMaterial = vmMaterialService.toggleDownloadButton(id);
        return ResponseEntity.ok(updatedMaterial);
    }

}
