package com.testapplication.controller;

import com.testapplication.dto.Response.AnswerKeyResponse;
import com.testapplication.service.AnswerKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/answerkeys")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AnswerKeyController {

    private final AnswerKeyService answerKeyService;

    // Save Answer Key with PDF
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AnswerKeyResponse saveAnswerKey(
            @RequestParam String title,
            @RequestParam(required = false) String link,
            @RequestParam Long examId,
            @RequestParam(defaultValue = "true") Boolean active,
            @RequestParam("pdf") MultipartFile pdf) {

        return answerKeyService.saveAnswerKey(title, link, examId, active, pdf);
    }

    // Get All
    @GetMapping
    public List<AnswerKeyResponse> getAllAnswerKeys() {
        return answerKeyService.getAllAnswerKeys();
    }

    // Get By Id
    @GetMapping("/{id}")
    public AnswerKeyResponse getAnswerKeyById(@PathVariable Long id) {
        return answerKeyService.getAnswerKeyById(id);
    }

    // Update
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AnswerKeyResponse updateAnswerKey(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam(required = false) String link,
            @RequestParam Long examId,
            @RequestParam Boolean active,
            @RequestParam(value = "pdf", required = false) MultipartFile pdf) {

        return answerKeyService.updateAnswerKey(id, title, link, examId, active, pdf);
    }

    // Delete
    @DeleteMapping("/{id}")
    public String deleteAnswerKey(@PathVariable Long id) {

        answerKeyService.deleteAnswerKey(id);

        return "Answer Key Deleted Successfully";
    }

    // Download PDF
    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {

        byte[] pdf = answerKeyService.downloadPdf(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}