package com.testapplication.service;

import com.testapplication.dto.Request.DownloadRequest;
import com.testapplication.dto.Response.DownloadResponse;

import java.util.List;

public interface DownloadService {

    DownloadResponse saveDownload(DownloadRequest request);

    DownloadResponse updateDownload(Long id, DownloadRequest request);

    void deleteDownload(Long id);

    DownloadResponse getDownloadById(Long id);

    List<DownloadResponse> getAllDownloads();
}