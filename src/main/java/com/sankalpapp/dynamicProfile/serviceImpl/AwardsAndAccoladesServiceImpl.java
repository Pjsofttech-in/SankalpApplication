package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebAwardsAndAccolades;
import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.dynamicProfile.repository.AwardsAndAccoladesRepository;
import com.sankalpapp.dynamicProfile.repository.SecurityUrlrepository;
import com.sankalpapp.dynamicProfile.service.AwardsAndAccoladesService;
import com.sankalpapp.dynamicProfile.service.PermissionService;
import com.sankalpapp.dynamicProfile.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AwardsAndAccoladesServiceImpl implements AwardsAndAccoladesService {

    @Autowired
    private AwardsAndAccoladesRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SecurityUrlrepository securityUrlRepository;

    @Autowired
    private S3Service s3Service;

    private void validateUrlExists(String url, String branchCode)
    {
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
    public WebAwardsAndAccolades createAward(WebAwardsAndAccolades award, String role, String email, MultipartFile awardImage, String url) {
        validateUrlExists(url,null);

        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create award");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        WebSecurityUrl webSecurityUrl = securityUrlRepository.findByUrl(normalizeUrl(url))
                .orElseThrow(() -> new ResourceNotFoundException("Provided URL does not exist in security URL table"));

        // Static color logic: Use color from first record if exists
        List<WebAwardsAndAccolades> existing = repository.findAll();
        if (!existing.isEmpty()) {
            award.setAwardColour(existing.get(0).getAwardColour());
        }

        award.setRole(role);
        award.setCreatedByEmail(email);
        award.setBranchCode(branchCode);
        award.setUrl(url);
        award.setWebSecurityUrl(webSecurityUrl);

        if (awardImage != null && !awardImage.isEmpty()) {
            try {
                String imageUrl = s3Service.uploadImage(awardImage, branchCode);
                award.setAwardImage(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload Award image", e);
            }
        }

        return repository.save(award);
    }


    @Override
    public List<WebAwardsAndAccolades> getAllAwardsByBranchCode(String role, String email, String url, String branchCode) {
        validateUrlExists(url,branchCode);
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view awards");
        }
        return repository.findAllByBranchCode(branchCode);
    }


    @Override
    public WebAwardsAndAccolades updateAward(Long id, WebAwardsAndAccolades award, String role, String email, MultipartFile awardImage, String url) {
        validateUrlExists(url,null);

        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update award");
        }

        WebAwardsAndAccolades existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Award not found"));

        existing.setAwardName(award.getAwardName() != null ? award.getAwardName() : existing.getAwardName());
        existing.setDescription(award.getDescription() != null ? award.getDescription() : existing.getDescription());
        existing.setAwardedBy(award.getAwardedBy() != null ? award.getAwardedBy() : existing.getAwardedBy());
        existing.setYear(award.getYear() != 0 ? award.getYear() : existing.getYear());
        existing.setAwardTo(award.getAwardTo() != null ? award.getAwardTo() : existing.getAwardTo());
        existing.setUrl(award.getUrl() != null ? award.getUrl() : existing.getUrl());

        String branchCode = permissionService.fetchBranchCode(role, email);

        // Static color logic: If color changed, update all
        if (award.getAwardColour() != null && !award.getAwardColour().equals(existing.getAwardColour())) {
            List<WebAwardsAndAccolades> allAwards = repository.findAll();
            for (WebAwardsAndAccolades a : allAwards) {
                a.setAwardColour(award.getAwardColour());
            }
            repository.saveAll(allAwards);
        }

        if (awardImage != null && !awardImage.isEmpty()) {
            try {
                String imageUrl = s3Service.uploadImage(awardImage, branchCode);

                if (existing.getAwardImage() != null && existing.getAwardImage().contains("amazonaws.com")) {
                    s3Service.deleteImage(existing.getAwardImage());
                }

                existing.setAwardImage(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload Award image", e);
            }
        }

        return repository.save(existing);
    }


    @Override
    public void deleteAward(Long id, String role, String email, String url) {
        validateUrlExists(url,null);

        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete award");
        }

        WebAwardsAndAccolades award = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Award not found"));

        if (award.getAwardImage() != null && award.getAwardImage().contains("amazonaws.com")) {
            s3Service.deleteImage(award.getAwardImage());
        }

        repository.deleteById(id);
    }

    @Override
    public WebAwardsAndAccolades getAwardById(Long id, String role, String email, String url) {
        validateUrlExists(url,null);

        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view award");
        }

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Award not found"));
    }
}
