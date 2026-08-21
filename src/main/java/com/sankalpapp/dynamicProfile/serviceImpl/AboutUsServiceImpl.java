package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebAboutUs;
import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.exception.AlreadyExistsException;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.dynamicProfile.repository.AboutUsRepository;
import com.sankalpapp.dynamicProfile.repository.SecurityUrlrepository;
import com.sankalpapp.dynamicProfile.service.AboutUsService;
import com.sankalpapp.dynamicProfile.service.PermissionService;
import com.sankalpapp.dynamicProfile.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AboutUsServiceImpl implements AboutUsService {

    @Autowired
    private AboutUsRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SecurityUrlrepository securityUrlRepository;

    @Autowired
    private S3Service s3Service;

    private void validateUrlExists(String url, String branchCode) {

        String normalizedUrl = normalizeUrl(url);
        if (branchCode == null || branchCode.isBlank()) {
            securityUrlRepository.findByUrl(normalizedUrl)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Provided URL [" + url + "] does not exist"
                    ));
            return;
        }

        securityUrlRepository.findByUrlAndBranchCode(normalizedUrl, branchCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "URL [" + url + "] is not allowed for branchCode [" + branchCode + "]"
                ));
    }


    private String normalizeUrl(String url) {
        return (url == null) ? "" : url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebAboutUs createAboutUs(WebAboutUs webAboutUs, String role, String email, MultipartFile aboutUsImage, String url) {
        validateUrlExists(url,null);

        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create AboutUs");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);

        // ✅ Prevent duplicate creation
        repository.findFirstByBranchCode(branchCode).ifPresent(existing -> {
            throw new AlreadyExistsException("AboutUs content already exists for this branch");
        });

        WebSecurityUrl webSecurityUrl = securityUrlRepository.findByUrl(normalizeUrl(url))
                .orElseThrow(() -> new ResourceNotFoundException("Provided URL does not exist in security URL table"));

        webAboutUs.setRole(role);
        webAboutUs.setCreatedByEmail(email);
        webAboutUs.setBranchCode(branchCode);
        webAboutUs.setUrl(url);
        webAboutUs.setWebSecurityUrl(webSecurityUrl);

        if (aboutUsImage != null && !aboutUsImage.isEmpty()) {
            try {
                String imageUrl = s3Service.uploadImage(aboutUsImage, branchCode);
                webAboutUs.setAboutUsImage(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload AboutUs image", e);
            }
        }

        return repository.save(webAboutUs);
    }



    @Override
    public List<WebAboutUs> getAllAboutUsByBranchCode(String role, String email, String url, String branchCode) {
        validateUrlExists(url,branchCode);
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view AboutUs");
        }

        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public WebAboutUs updateAboutUs(int id, WebAboutUs webAboutUs, String role, String email, MultipartFile aboutUsImage, String url) {
        validateUrlExists(url,null);
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update AboutUs");
        }

        WebAboutUs existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AboutUs not found"));

        existing.setAboutUsTitle(webAboutUs.getAboutUsTitle() != null ? webAboutUs.getAboutUsTitle() : existing.getAboutUsTitle());
        existing.setAboutUsDescription(webAboutUs.getAboutUsDescription() != null ? webAboutUs.getAboutUsDescription() : existing.getAboutUsDescription());
        existing.setUrl(webAboutUs.getUrl() != null ? webAboutUs.getUrl() : existing.getUrl());

        String branchCode = permissionService.fetchBranchCode(role, email);

        if (aboutUsImage != null && !aboutUsImage.isEmpty()) {
            try {
                // Upload new image
                String imageUrl = s3Service.uploadImage(aboutUsImage, branchCode);

                // Optional: delete old image if exists
                if (existing.getAboutUsImage() != null && existing.getAboutUsImage().contains("amazonaws.com")) {
                    s3Service.deleteImage(existing.getAboutUsImage());
                }

                existing.setAboutUsImage(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload AboutUs image", e);
            }
        }

        return repository.save(existing);
    }

    @Override
    public void deleteAboutUs(int id, String role, String email, String url) {
        validateUrlExists(url,null);
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete AboutUs");
        }

        WebAboutUs webAboutUs = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AboutUs not found"));

        // Delete image from S3 if exists
        if (webAboutUs.getAboutUsImage() != null && webAboutUs.getAboutUsImage().contains("amazonaws.com")) {
            s3Service.deleteImage(webAboutUs.getAboutUsImage());
        }

        repository.deleteById(id);
    }

    @Override
    public WebAboutUs getAboutUsById(int id, String role, String email, String url) {
        validateUrlExists(url,null);
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view AboutUs");
        }

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AboutUs not found"));
    }
}
