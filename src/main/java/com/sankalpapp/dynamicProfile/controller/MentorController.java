package com.sankalpapp.dynamicProfile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalpapp.dynamicProfile.dto.MentorDTO;
import com.sankalpapp.dynamicProfile.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/mentors")
@RequiredArgsConstructor
public class MentorController {

    private final MentorService mentorService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<MentorDTO> createMentor(
            @RequestPart("mentor") String mentorJson,
            @RequestParam("mentorImage") MultipartFile imageFile) throws JsonProcessingException {

        MentorDTO dto = objectMapper.readValue(mentorJson, MentorDTO.class);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mentorService.createMentor(dto, imageFile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MentorDTO> updateMentor(
            @PathVariable Long id,
            @RequestPart("mentor") String mentorJson,
            @RequestParam("mentorImage") MultipartFile imageFile) throws JsonProcessingException {

        MentorDTO dto = objectMapper.readValue(mentorJson, MentorDTO.class);
        return ResponseEntity.ok(
                mentorService.updateMentor(id, dto, imageFile)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentorDTO> getMentorById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                mentorService.getMentorById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<MentorDTO>> getAllMentors() {

        return ResponseEntity.ok(
                mentorService.getAllMentors()
        );
    }

    @GetMapping("/active")
    public ResponseEntity<List<MentorDTO>> getActiveMentors() {

        return ResponseEntity.ok(
                mentorService.getActiveMentors()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentor(
            @PathVariable Long id) {

        mentorService.deleteMentor(id);

        return ResponseEntity.noContent().build();
    }
}