package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebAboutUs;
import com.sankalpapp.dynamicProfile.repository.AboutUsRepository;
import com.sankalpapp.dynamicProfile.service.AboutUsService;
import com.sankalpapp.dynamicProfile.service.S3Service;
import com.sankalpapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AboutUsServiceImpl implements AboutUsService {

    @Autowired
    private AboutUsRepository repository;

    @Autowired
    private S3Service s3Service;

    private String normalizeUrl(String url) {
        return (url == null) ? "" : url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebAboutUs createAboutUs(WebAboutUs webAboutUs, MultipartFile aboutUsImage, String url) {
        webAboutUs.setUrl(url);

//        if (aboutUsImage != null && !aboutUsImage.isEmpty()) {
//            try {
//                String imageUrl = s3Service.uploadImage(aboutUsImage);
//                webAboutUs.setAboutUsImage(imageUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload AboutUs image", e);
//            }
//        }

        return repository.save(webAboutUs);
    }


    @Override
    public List<WebAboutUs> getAllAboutUsByBranchCode(String url) {
        return repository.findAllOrderById();
    }

    @Override
    public WebAboutUs updateAboutUs(int id, WebAboutUs webAboutUs, MultipartFile aboutUsImage, String url) {
        WebAboutUs existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AboutUs not found"));

        existing.setAboutUsTitle(webAboutUs.getAboutUsTitle() != null ? webAboutUs.getAboutUsTitle() : existing.getAboutUsTitle());
        existing.setAboutUsDescription(webAboutUs.getAboutUsDescription() != null ? webAboutUs.getAboutUsDescription() : existing.getAboutUsDescription());
        existing.setUrl(webAboutUs.getUrl() != null ? webAboutUs.getUrl() : existing.getUrl());
        existing.setTotalExamCenters(webAboutUs.getTotalExamCenters());
        existing.setTotalFaculties(webAboutUs.getTotalFaculties());
        existing.setTotalStudents(webAboutUs.getTotalStudents());
        existing.setTotalYearsOfExcellence(webAboutUs.getTotalYearsOfExcellence());

//        if (aboutUsImage != null && !aboutUsImage.isEmpty()) {
//            try {
//                // Upload new image
//                String imageUrl = s3Service.uploadImage(aboutUsImage);
//
//                // Optional: delete old image if exists
//                if (existing.getAboutUsImage() != null && existing.getAboutUsImage().contains("amazonaws.com")) {
//                    s3Service.deleteImage(existing.getAboutUsImage());
//                }
//
//                existing.setAboutUsImage(imageUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload AboutUs image", e);
//            }
//        }

        return repository.save(existing);
    }

    @Override
    public void deleteAboutUs(int id, String url) {
        WebAboutUs webAboutUs = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AboutUs not found"));

        // Delete image from S3 if exists
        if (webAboutUs.getAboutUsImage() != null && webAboutUs.getAboutUsImage().contains("amazonaws.com")) {
            s3Service.deleteImage(webAboutUs.getAboutUsImage());
        }

        repository.deleteById(id);
    }

    @Override
    public WebAboutUs getAboutUsById(int id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AboutUs not found"));
    }
}
