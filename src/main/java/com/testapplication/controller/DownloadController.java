package com.testapplication.controller;

import com.testapplication.dto.Request.DownloadRequest;
import com.testapplication.dto.Response.DownloadResponse;
import com.testapplication.service.DownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/downloads")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DownloadController {

    private final DownloadService downloadService;

    @PostMapping
    public DownloadResponse saveDownload(@RequestBody DownloadRequest request) {
        return downloadService.saveDownload(request);
    }

    @GetMapping
    public List<DownloadResponse> getAllDownloads() {
        return downloadService.getAllDownloads();
    }

    @GetMapping("/{id}")
    public DownloadResponse getDownloadById(@PathVariable Long id) {
        return downloadService.getDownloadById(id);
    }

    @PutMapping("/{id}")
    public DownloadResponse updateDownload(@PathVariable Long id,
                                           @RequestBody DownloadRequest request) {
        return downloadService.updateDownload(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteDownload(@PathVariable Long id) {

        downloadService.deleteDownload(id);

        return "Download deleted successfully.";
    }
}