package com.sankalpapp.controller;

import com.sankalpapp.entity.Syllabus;
import com.sankalpapp.service.SyllabusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SyllabusController {

    private final SyllabusService syllabusService;

    public SyllabusController(SyllabusService syllabusService) {
        this.syllabusService = syllabusService;
    }

    @PostMapping("/createSyllabus")
    public ResponseEntity<Syllabus> createSyllabus(
            @RequestBody Syllabus syllabus) {

        return ResponseEntity.ok(
                syllabusService.createSyllabus(syllabus)
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

    @PutMapping("/updateSyllabus/{id}")
    public ResponseEntity<Syllabus> updateSyllabus(
            @PathVariable Long id,
            @RequestBody Syllabus syllabus) {

        return ResponseEntity.ok(
                syllabusService.updateSyllabus(id, syllabus)
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