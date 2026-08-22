package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.dynamicProfile.entity.WebVisionMission;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.dynamicProfile.repository.SecurityUrlrepository;
import com.sankalpapp.dynamicProfile.repository.VisionMissionRepository;
import com.sankalpapp.dynamicProfile.service.S3Service;
import com.sankalpapp.dynamicProfile.service.VisionMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class VisionMissionServiceImpl implements VisionMissionService {

    @Autowired
    private VisionMissionRepository repository;

    @Autowired
    private SecurityUrlrepository securityUrlRepository;

    @Autowired
    private S3Service s3Service;

    private void validateUrlExists(String url) {

        String normalizedUrl = normalizeUrl(url);

        securityUrlRepository.findByUrl(normalizedUrl)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "URL [" + url + "] is not allowed"
                ));
    }


    private String normalizeUrl(String url) {
        return (url == null) ? "" : url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebVisionMission create(WebVisionMission vm, MultipartFile directorImage, String url) {
        //validateUrlExists;
        // ❗ Prevent duplicate creation per branch

        WebSecurityUrl webSecurityUrl = securityUrlRepository.findByUrl(normalizeUrl(url))
                .orElseThrow(() -> new ResourceNotFoundException("Provided URL does not exist in security URL table"));

        vm.setUrl(url);
        vm.setWebSecurityUrl(webSecurityUrl);

        if (directorImage != null && !directorImage.isEmpty()) {
//            try {
//                String imageUrl = s3Service.uploadImage(directorImage);
//                vm.setDirectorImage(imageUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload director image", e);
//            }
        }

        return repository.save(vm);
    }


    @Override
    public List<WebVisionMission> getAllByBranchCode(String url) {
         return repository.findAllOrderById();
    }


    @Override
    public WebVisionMission update(Long id, WebVisionMission vm, MultipartFile directorImage, String url) {
        //validateUrlExists;
        WebVisionMission existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VisionMission not found"));

        existing.setVision(vm.getVision() != null ? vm.getVision() : existing.getVision());
        existing.setMission(vm.getMission() != null ? vm.getMission() : existing.getMission());
        existing.setDirectorMessage(vm.getDirectorMessage() != null ? vm.getDirectorMessage() : existing.getDirectorMessage());
        existing.setDirectorName(vm.getDirectorName() != null ? vm.getDirectorName() : existing.getDirectorName());
        existing.setDescription(vm.getDescription() != null ? vm.getDescription() : existing.getDescription());
        existing.setVisionmissionColor(vm.getVisionmissionColor() != null ? vm.getVisionmissionColor() : existing.getVisionmissionColor());
        existing.setUrl(vm.getUrl() != null ? vm.getUrl() : existing.getUrl());

        if (directorImage != null && !directorImage.isEmpty()) {
//            try {
//                // Upload new image
//                String imageUrl = s3Service.uploadImage(directorImage);
//
//                // Delete old image if exists and was uploaded to S3
//                if (existing.getDirectorImage() != null && existing.getDirectorImage().contains("amazonaws.com")) {
//                    s3Service.deleteImage(existing.getDirectorImage());
//                }
//
//                existing.setDirectorImage(imageUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload director image", e);
//            }
        }

        return repository.save(existing);
    }

    @Override
    public void delete(Long id, String url) {
         WebVisionMission vm = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VisionMission not found"));

        // Delete image from S3 if exists
        if (vm.getDirectorImage() != null && vm.getDirectorImage().contains("amazonaws.com")) {
            s3Service.deleteImage(vm.getDirectorImage());
        }

        repository.deleteById(id);
    }

    @Override
    public WebVisionMission getById(Long id, String url) {
         return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VisionMission not found"));
    }
}
