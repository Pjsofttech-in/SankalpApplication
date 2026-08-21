package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebHRDetails;
import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.dynamicProfile.repository.SecurityUrlrepository;
import com.sankalpapp.dynamicProfile.repository.WebHRDetailsRepository;
import com.sankalpapp.dynamicProfile.service.PermissionService;
import com.sankalpapp.dynamicProfile.service.WebHRDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebHRDetailsServiceImpl implements WebHRDetailsService {

    @Autowired
    private WebHRDetailsRepository repository;

    @Autowired
    private SecurityUrlrepository securityUrlRepository;

    @Autowired
    private PermissionService permissionService;

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
        if (url == null) return "";
        return url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebHRDetails create(WebHRDetails webHRDetails, String role, String email, String url) {
        validateUrlExists(url,null);

        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create WebHRDetails");
        }

        String branchCode = permissionService.fetchBranchCode(role,email);

        String normalizedUrl = normalizeUrl(url);
        WebSecurityUrl webSecurityUrl = securityUrlRepository.findByUrl(normalizedUrl)
                .orElseThrow(() -> new ResourceNotFoundException("Provided URL does not exist"));

        webHRDetails.setWebSecurityUrl(webSecurityUrl);
        webHRDetails.setCreatedByEmail(email);
        webHRDetails.setRole(role);
        webHRDetails.setBranchCode(branchCode);
        webHRDetails.setUrl(url);
        return repository.save(webHRDetails);
    }

    @Override
    public List<WebHRDetails> getAllByBranchCode(String role, String email, String url, String branchCode) {
        validateUrlExists(url,branchCode);
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view WebHRDetails");
        }
        return repository.findAllByBranchCode(branchCode);
    }


    @Override
    public WebHRDetails getById(Long id, String role, String email, String url) {
        validateUrlExists(url,null);

        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view WebHRDetails");
        }

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WebHRDetails not found"));
    }

    @Override
    public WebHRDetails update(Long id, WebHRDetails webHRDetails, String role, String email, String url) {
        validateUrlExists(url,null);

        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update WebHRDetails");
        }

        WebHRDetails existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WebHRDetails not found"));

        existing.setHrName(webHRDetails.getHrName() != null ? webHRDetails.getHrName() : existing.getHrName());
        existing.setEmail(webHRDetails.getEmail() != null ? webHRDetails.getEmail() : existing.getEmail());
        existing.setContact(webHRDetails.getContact() != null ? webHRDetails.getContact() : existing.getContact());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id, String role, String email, String url) {
        validateUrlExists(url,null);

        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete WebHRDetails");
        }

        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WebHRDetails not found"));
        repository.deleteById(id);
    }
}