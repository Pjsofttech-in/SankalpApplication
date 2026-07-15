package com.testapplication.serviceimpl;

import com.testapplication.entity.Download;
import com.testapplication.repository.DownloadRepository;
import com.testapplication.service.DownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DownloadServiceImpl implements DownloadService {

    private final DownloadRepository repository;

    @Override
    public Download saveDownload(Download download) {
        return repository.save(download);
    }

    @Override
    public Download updateDownload(Long id, Download download) {

        Download existing = getDownloadById(id);

        existing.setTitle(download.getTitle());
        existing.setDescription(download.getDescription());
        existing.setFileName(download.getFileName());
        existing.setFilePath(download.getFilePath());
        existing.setActive(download.getActive());

        return repository.save(existing);
    }

    @Override
    public void deleteDownload(Long id) {
        repository.delete(getDownloadById(id));
    }

    @Override
    public Download getDownloadById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Download not found."));
    }

    @Override
    public List<Download> getAllDownloads() {
        return repository.findAll();
    }
}