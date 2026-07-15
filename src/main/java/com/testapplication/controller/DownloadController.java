package com.testapplication.controller;

import com.testapplication.entity.Download;
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

    // Save Download
    @PostMapping
    public Download saveDownload(@RequestBody Download download) {
        return downloadService.saveDownload(download);
    }

    // Get All Downloads
    @GetMapping
    public List<Download> getAllDownloads() {
        return downloadService.getAllDownloads();
    }

    // Get Download By Id
    @GetMapping("/{id}")
    public Download getDownloadById(@PathVariable Long id) {
        return downloadService.getDownloadById(id);
    }

    // Update Download
    @PutMapping("/{id}")
    public Download updateDownload(@PathVariable Long id,
                                   @RequestBody Download download) {
        return downloadService.updateDownload(id, download);
    }

    // Delete Download
    @DeleteMapping("/{id}")
    public String deleteDownload(@PathVariable Long id) {
        downloadService.deleteDownload(id);
        return "Download deleted successfully.";
    }
}