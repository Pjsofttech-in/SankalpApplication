package com.sankalpapp.controller;

import com.sankalpapp.dto.Request.DownloadRequest;
import com.sankalpapp.dto.Response.DownloadResponse;
import com.sankalpapp.service.DownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public DownloadResponse saveDownload(@RequestBody DownloadRequest request) {

        return downloadService.saveDownload(request);
    }

    // Get All Downloads
    @GetMapping
    @PreAuthorize("permitAll()")
    public List<DownloadResponse> getAllDownloads() {

        return downloadService.getAllDownloads();
    }

    // Get Download By Id
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public DownloadResponse getDownloadById(@PathVariable Long id) {

        return downloadService.getDownloadById(id);
    }

    // Update Download
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public DownloadResponse updateDownload(@PathVariable Long id,
                                           @RequestBody DownloadRequest request) {

        return downloadService.updateDownload(id, request);
    }

    // Delete Download
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteDownload(@PathVariable Long id) {

        downloadService.deleteDownload(id);

        return "Download deleted successfully.";
    }
}