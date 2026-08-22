package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebManuBar;
import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.dynamicProfile.repository.ManuBarRepository;
import com.sankalpapp.dynamicProfile.repository.SecurityUrlrepository;
import com.sankalpapp.dynamicProfile.service.ManuBarService;
import com.sankalpapp.dynamicProfile.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ManuBarServiceImpl implements ManuBarService {

    @Autowired
    private ManuBarRepository repository;

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
    public WebManuBar createManuBar(WebManuBar webManuBar, MultipartFile menubarImage, String url) {
         WebSecurityUrl webSecurityUrl = securityUrlRepository.findByUrl(normalizeUrl(url))
                .orElseThrow(() -> new ResourceNotFoundException("Provided URL does not exist in security URL table"));

        webManuBar.setUrl(url);
        webManuBar.setWebSecurityUrl(webSecurityUrl);

//        if (menubarImage != null && !menubarImage.isEmpty()) {
//            try {
//                String imageUrl = s3Service.uploadImage(menubarImage);
//                webManuBar.setMenubarImage(imageUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload ManuBar image", e);
//            }
//        }

        return repository.save(webManuBar);
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
        existing.setMenubarName(webManuBar.getMenubarName()!=null ? webManuBar.getMenubarName():existing.getMenubarName());

//        if (menubarImage != null && !menubarImage.isEmpty()) {
//            try {
//                String imageUrl = s3Service.uploadImage(menubarImage);
//
//                if (existing.getMenubarImage() != null && existing.getMenubarImage().contains("amazonaws.com")) {
//                    s3Service.deleteImage(existing.getMenubarImage());
//                }
//
//                existing.setMenubarImage(imageUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload ManuBar image", e);
//            }
//        }

        return repository.save(existing);
    }

    @Override
    public void deleteManuBar(Long id, String url) {
         WebManuBar webManuBar = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ManuBar not found"));

        if (webManuBar.getMenubarImage() != null && webManuBar.getMenubarImage().contains("amazonaws.com")) {
            s3Service.deleteImage(webManuBar.getMenubarImage());
        }

        repository.deleteById(id);
    }

    @Override
    public WebManuBar getManuBarById(Long id, String url) {
         return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ManuBar not found"));
    }
}