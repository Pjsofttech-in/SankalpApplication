package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Request.DownloadRequest;
import com.sankalpapp.dto.Response.DownloadResponse;
import com.sankalpapp.entity.Download;
import com.sankalpapp.repository.DownloadRepository;
import com.sankalpapp.service.DownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DownloadServiceImpl implements DownloadService {

    private final DownloadRepository repository;

    @Override
    public DownloadResponse saveDownload(DownloadRequest request) {

        Download download = Download.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .fileName(request.getFileName())
                .filePath(request.getFilePath())
                .active(request.getActive())
                .build();

        return mapToResponse(repository.save(download));
    }

    @Override
    public DownloadResponse updateDownload(Long id, DownloadRequest request) {

        Download download = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Download not found."));

        download.setTitle(request.getTitle());
        download.setDescription(request.getDescription());
        download.setFileName(request.getFileName());
        download.setFilePath(request.getFilePath());
        download.setActive(request.getActive());

        return mapToResponse(repository.save(download));
    }

    @Override
    public void deleteDownload(Long id) {

        Download download = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Download not found."));

        repository.delete(download);
    }

    @Override
    public DownloadResponse getDownloadById(Long id) {

        Download download = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Download not found."));

        return mapToResponse(download);
    }

    @Override
    public List<DownloadResponse> getAllDownloads() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private DownloadResponse mapToResponse(Download download) {

        return DownloadResponse.builder()
                .id(download.getId())
                .title(download.getTitle())
                .description(download.getDescription())
                .fileName(download.getFileName())
                .filePath(download.getFilePath())
                .active(download.getActive())
                .build();
    }
}