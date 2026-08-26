package com.sankalpapp.controller;

import com.sankalpapp.dto.Response.AnswerKeyResponse;
import com.sankalpapp.service.AnswerKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public AnswerKeyResponse saveAnswerKey(
            @RequestParam String title,
            @RequestParam(required = false) String link,
            @RequestParam Long examId,
            @RequestParam(defaultValue = "true") Boolean active,
            @RequestParam("pdf") MultipartFile pdf) {

        return answerKeyService.saveAnswerKey(title, link, examId, active, pdf);
    }


    // Get All Answer Keys
    @GetMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public List<AnswerKeyResponse> getAllAnswerKeys() {

        return answerKeyService.getAllAnswerKeys();
    }


    // Get Answer Key By Id
    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public AnswerKeyResponse getAnswerKeyById(@PathVariable Long id) {

        return answerKeyService.getAnswerKeyById(id);
    }


    // Update Answer Key
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public AnswerKeyResponse updateAnswerKey(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam(required = false) String link,
            @RequestParam Long examId,
            @RequestParam Boolean active,
            @RequestParam(value = "pdf", required = false) MultipartFile pdf) {

        return answerKeyService.updateAnswerKey(
                id, title, link, examId, active, pdf);
    }


    // Delete Answer Key
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteAnswerKey(@PathVariable Long id) {

        answerKeyService.deleteAnswerKey(id);

        return "Answer Key Deleted Successfully";
    }


//    // Download PDF
//    @GetMapping("/{id}/download")
//    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
//    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
//
//        byte[] pdf = answerKeyService.downloadPdf(id);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(pdf);
//    }
}