package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebAboutUs;
import com.sankalpapp.dynamicProfile.repository.AboutUsRepository;
import com.sankalpapp.dynamicProfile.service.AboutUsService;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.serviceimpl.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AboutUsServiceImpl implements AboutUsService {

    @Autowired
    private AboutUsRepository repository;

    @Autowired
    private S3Service s3service;

    private static final String folder = "AboutUs";

    @Override
    public WebAboutUs createAboutUs(WebAboutUs webAboutUs, MultipartFile aboutUsImage, String url) {
        webAboutUs.setUrl(url);
        uploadFile(aboutUsImage, webAboutUs);
        return repository.save(webAboutUs);
    }

    private void uploadFile(MultipartFile pdf, WebAboutUs webAboutUs) {
        if (pdf != null) {
            try {
                String fileURL = s3service.uploadFile(pdf, folder);
                webAboutUs.setAboutUsImage(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
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

        uploadFile(aboutUsImage, existing);

        return repository.save(existing);
    }

    @Override
    public void deleteAboutUs(int id, String url) {
        WebAboutUs webAboutUs = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AboutUs not found"));

        s3service.deleteFile(webAboutUs.getUrl());

        repository.deleteById(id);
    }

    @Override
    public WebAboutUs getAboutUsById(int id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AboutUs not found"));
    }
}
