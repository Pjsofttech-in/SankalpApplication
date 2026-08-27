package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebVisionMission;
import com.sankalpapp.dynamicProfile.repository.VisionMissionRepository;
import com.sankalpapp.dynamicProfile.service.VisionMissionService;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.serviceimpl.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class VisionMissionServiceImpl implements VisionMissionService {

    @Autowired
    private VisionMissionRepository repository;

    @Autowired
    private S3Service s3Service;

    private static final String folder = "Vision";

    @Override
    public WebVisionMission create(WebVisionMission vm, MultipartFile directorImage, String url) {

        vm.setUrl(url);
        uploadFile(directorImage, vm);

        return repository.save(vm);
    }


    @Override
    public List<WebVisionMission> getAllByBranchCode(String url) {
        return repository.findAllOrderById();
    }


    @Override
    public WebVisionMission update(Long id, WebVisionMission vm, MultipartFile directorImage, String url) {
        WebVisionMission existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VisionMission not found"));

        existing.setVision(vm.getVision() != null ? vm.getVision() : existing.getVision());
        existing.setMission(vm.getMission() != null ? vm.getMission() : existing.getMission());
        existing.setDirectorMessage(vm.getDirectorMessage() != null ? vm.getDirectorMessage() : existing.getDirectorMessage());
        existing.setDirectorName(vm.getDirectorName() != null ? vm.getDirectorName() : existing.getDirectorName());
        existing.setDescription(vm.getDescription() != null ? vm.getDescription() : existing.getDescription());
        existing.setVisionmissionColor(vm.getVisionmissionColor() != null ? vm.getVisionmissionColor() : existing.getVisionmissionColor());
        existing.setUrl(vm.getUrl() != null ? vm.getUrl() : existing.getUrl());

        uploadFile(directorImage, existing);

        return repository.save(existing);
    }

    private void uploadFile(MultipartFile pdf, WebVisionMission obj) {
        if (pdf != null) {
            try {
                String fileURL = s3Service.uploadFile(pdf, folder);
                obj.setDirectorImage(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
    }

    @Override
    public void delete(Long id, String url) {
        WebVisionMission vm = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VisionMission not found"));

        s3Service.deleteFileByUrl(vm.getDirectorImage());

        repository.deleteById(id);
    }

    @Override
    public WebVisionMission getById(Long id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VisionMission not found"));
    }
}
