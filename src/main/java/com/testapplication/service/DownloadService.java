package com.testapplication.service;

import com.testapplication.entity.Download;

import java.util.List;

public interface DownloadService {

    Download saveDownload(Download download);

    Download updateDownload(Long id, Download download);

    void deleteDownload(Long id);

    Download getDownloadById(Long id);

    List<Download> getAllDownloads();
}