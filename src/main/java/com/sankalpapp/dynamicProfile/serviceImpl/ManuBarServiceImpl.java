package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebManuBar;
import com.sankalpapp.dynamicProfile.repository.ManuBarRepository;
import com.sankalpapp.dynamicProfile.service.ManuBarService;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.serviceimpl.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ManuBarServiceImpl implements ManuBarService {

    private static final String folder = "MenuBar";
    @Autowired
    private ManuBarRepository repository;
    @Autowired
    private S3Service s3Service;

    @Override
    public WebManuBar createManuBar(WebManuBar webManuBar, MultipartFile menubarImage, String url) {
        webManuBar.setUrl(url);

        uploadFile(menubarImage, webManuBar);

        return repository.save(webManuBar);
    }

    private void uploadFile(MultipartFile pdf, WebManuBar obj) {
        if (pdf != null) {
            try {
                String fileURL = s3Service.uploadFile(pdf, folder);
                obj.setMenubarImage(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
    }


    @Override
    public List<WebManuBar> getAllByBranchCode(String url) {
        return repository.findAllOrderById();
    }


    @Override
    public WebManuBar updateManuBar(Long id, WebManuBar webManuBar, MultipartFile menubarImage, String url) {
        WebManuBar existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ManuBar not found"));

        existing.setManuBarColor(webManuBar.getManuBarColor() != null ? webManuBar.getManuBarColor() : existing.getManuBarColor());
        existing.setUrl(webManuBar.getUrl() != null ? webManuBar.getUrl() : existing.getUrl());
        existing.setMenubarName(webManuBar.getMenubarName() != null ? webManuBar.getMenubarName() : existing.getMenubarName());

        uploadFile(menubarImage, existing);

        return repository.save(existing);
    }

    @Override
    public void deleteManuBar(Long id, String url) {
        WebManuBar webManuBar = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ManuBar not found"));

        s3Service.deleteFileByUrl(webManuBar.getMenubarImage());

        repository.deleteById(id);
    }

    @Override
    public WebManuBar getManuBarById(Long id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ManuBar not found"));
    }
}