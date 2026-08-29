package com.sankalpapp.service;

import com.sankalpapp.dto.request.DownloadRequest;
import com.sankalpapp.dto.response.DownloadResponse;

import java.util.List;

public interface DownloadService {

    DownloadResponse saveDownload(DownloadRequest request);

    DownloadResponse updateDownload(Long id, DownloadRequest request);

    void deleteDownload(Long id);

    DownloadResponse getDownloadById(Long id);

    List<DownloadResponse> getAllDownloads();
}