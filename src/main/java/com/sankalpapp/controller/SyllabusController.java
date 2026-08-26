package com.sankalpapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalpapp.entity.Syllabus;
import com.sankalpapp.service.SyllabusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SyllabusController {

    private final SyllabusService syllabusService;
    private final ObjectMapper objectMapper;

    @PostMapping(value = "/createSyllabus", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Syllabus> createSyllabus(
            @RequestParam("syllabus") String syllabusJson,
            @RequestParam("syllabusFile") MultipartFile syllabusFile) throws JsonProcessingException {

        Syllabus syllabus = objectMapper.readValue(syllabusJson, Syllabus.class);
        return ResponseEntity.ok(
                syllabusService.createSyllabus(syllabus, syllabusFile)
        );
    }

    @GetMapping("/getAllSyllabus")
    public ResponseEntity<List<Syllabus>> getAllSyllabus() {

        return ResponseEntity.ok(
                syllabusService.getAllSyllabus()
        );
    }

    @GetMapping("/getSyllabusById/{id}")
    public ResponseEntity<Syllabus> getSyllabusById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                syllabusService.getSyllabusById(id)
        );
    }

    @PutMapping(value = "/updateSyllabus/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Syllabus> updateSyllabus(
            @PathVariable Long id,
            @RequestParam("syllabus") String syllabusJson,
            @RequestParam("syllabusFile") MultipartFile syllabusFile) throws JsonProcessingException {
        Syllabus syllabus = objectMapper.readValue(syllabusJson, Syllabus.class);
        return ResponseEntity.ok(
                syllabusService.updateSyllabus(id, syllabus, syllabusFile)
        );
    }

    @DeleteMapping("/deleteSyllabus/{id}")
    public ResponseEntity<String> deleteSyllabus(
            @PathVariable Long id) {

        syllabusService.deleteSyllabus(id);

        return ResponseEntity.ok(
                "Syllabus deleted successfully"
        );
    }
}