package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebMapAndImages;
import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.exception.AlreadyExistsException;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.dynamicProfile.repository.MapAndImagesRepository;
import com.sankalpapp.dynamicProfile.repository.SecurityUrlrepository;
import com.sankalpapp.dynamicProfile.service.MapAndImagesService;
import com.sankalpapp.dynamicProfile.service.PermissionService;
import com.sankalpapp.dynamicProfile.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MapAndImagesServiceImpl implements MapAndImagesService {

    @Autowired
    private MapAndImagesRepository repository;

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
    public WebMapAndImages create(WebMapAndImages entity, String role, String email, MultipartFile imageFile, String url) {
        validateUrlExists(url,null);

        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create MapAndImages");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);

        //   Prevent duplicate creation for the same branch
        repository.findFirstByBranchCode(branchCode).ifPresent(existing -> {
            throw new AlreadyExistsException("Map and Image already exists for this branch");
        });

        WebSecurityUrl webSecurityUrl = securityUrlRepository.findByUrl(normalizeUrl(url))
                .orElseThrow(() -> new ResourceNotFoundException("Provided URL does not exist in security URL table"));

        entity.setRole(role);
        entity.setCreatedByEmail(email);
        entity.setBranchCode(branchCode);
        entity.setUrl(url);
        entity.setWebSecurityUrl(webSecurityUrl);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imageUrl = s3Service.uploadImage(imageFile, branchCode);
                entity.setContactImage(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload contact image", e);
            }
        }

        return repository.save(entity);
    }


    @Override
    public List<WebMapAndImages> getAllByBranchCode(String role, String email, String url, String branchCode) {
        validateUrlExists(url,branchCode);

        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view MapAndImages");
        }

        return repository.findAllByBranchCode(branchCode);
    }


    @Override
    public WebMapAndImages update(Long id, WebMapAndImages updated, String role, String email, MultipartFile imageFile, String url) {
        validateUrlExists(url,null);
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update MapAndImages");
        }

        WebMapAndImages existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MapAndImages not found"));

        existing.setMaps(updated.getMaps() != null ? updated.getMaps() : existing.getMaps());
        existing.setUrl(updated.getUrl() != null ? updated.getUrl() : existing.getUrl());

        String branchCode = permissionService.fetchBranchCode(role, email);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String newImageUrl = s3Service.uploadImage(imageFile, branchCode);

                // Optional: delete old image if stored in S3
                if (existing.getContactImage() != null && existing.getContactImage().contains("amazonaws.com")) {
                    s3Service.deleteImage(existing.getContactImage());
                }

                existing.setContactImage(newImageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload contact image", e);
            }
        }

        return repository.save(existing);
    }

    @Override
    public void delete(Long id, String role, String email, String url) {
        validateUrlExists(url,null);
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete MapAndImages");
        }

        WebMapAndImages entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MapAndImages not found"));

        if (entity.getContactImage() != null && entity.getContactImage().contains("amazonaws.com")) {
            s3Service.deleteImage(entity.getContactImage());
        }

        repository.deleteById(id);
    }

    @Override
    public WebMapAndImages getById(Long id, String role, String email, String url) {
        validateUrlExists(url,null);
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view MapAndImages");
        }

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MapAndImages not found"));
    }
}