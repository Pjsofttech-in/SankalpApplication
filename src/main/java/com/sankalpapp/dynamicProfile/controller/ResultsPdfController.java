package com.sankalpapp.dynamicProfile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalpapp.dynamicProfile.entity.ResultsPdf;
import com.sankalpapp.dynamicProfile.service.ResultsPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/results-pdfs")
@RequiredArgsConstructor
public class ResultsPdfController {

    private final ResultsPdfService resultsPdfService;
    private final ObjectMapper mapper;

    @GetMapping
    public ResponseEntity<List<ResultsPdf>> getAllResultsPdfs() {
        return ResponseEntity.ok(resultsPdfService.getAllResultsPdfs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultsPdf> getResultsPdfById(@PathVariable Long id) {
        return ResponseEntity.ok(
                resultsPdfService.getResultsPdfById(id)
        );
    }

    @PostMapping
    public ResponseEntity<ResultsPdf> createResultsPdf(
            @RequestParam(required = false) String resultsPdfJson,
            @RequestPart(required = false) MultipartFile resultPdf) throws JsonProcessingException {

        ResultsPdf resultsPdf = mapper.readValue(resultsPdfJson, ResultsPdf.class);
        ResultsPdf savedPdf =
                resultsPdfService.createResultsPdf(resultsPdf, resultPdf);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedPdf);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultsPdf> updateResultsPdf(
            @PathVariable Long id,
            @RequestBody ResultsPdf resultsPdf,
            @RequestPart(required = false) MultipartFile resultPdf) {

        ResultsPdf updatedPdf =
                resultsPdfService.updateResultsPdf(id, resultsPdf, resultPdf);

        return ResponseEntity.ok(updatedPdf);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResultsPdf(@PathVariable Long id) {

        resultsPdfService.deleteResultsPdf(id);

        return ResponseEntity.noContent().build();
    }
}