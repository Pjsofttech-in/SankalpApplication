package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.exception.AlreadyExistsException;
import com.sankalpapp.dynamicProfile.repository.SecurityUrlrepository;
import com.sankalpapp.dynamicProfile.service.PermissionService;
import com.sankalpapp.dynamicProfile.service.SecurityUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityUrlServiceImpl implements SecurityUrlService {

    @Autowired
    private SecurityUrlrepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public WebSecurityUrl create(WebSecurityUrl webSecurityUrl, String role, String email) {
        if (!permissionService.hasPermission(role,email,"POST")){
            throw new AccessDeniedException("No permission to create URL");
        }
         String branchCode=permissionService.fetchBranchCode(role,email);
        webSecurityUrl.setBranchCode(branchCode);
        webSecurityUrl.setCreatedByEmail(email);
        webSecurityUrl.setRole(role);
        if (webSecurityUrl.getBranchCode() == null || webSecurityUrl.getBranchCode().isBlank()) {
            throw new IllegalArgumentException("Branch code is required");
        }

        // Ensure only one SecurityUrl per branch
        boolean exists = repository.existsByBranchCode(webSecurityUrl.getBranchCode());
        if (exists) {
            throw new AlreadyExistsException("Security URL already created for this branch");

        }

        return repository.save(webSecurityUrl);
    }


    @Override
    public List<WebSecurityUrl> getAllByBranchCode(String role, String email, String branchCode) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to fetch security URLs by branch code");
        }
        return repository.findAllByBranchCode(branchCode);
    }


    @Override
    public WebSecurityUrl update(long id, WebSecurityUrl webSecurityUrl, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update security URL");
        }

        WebSecurityUrl existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Security URL not found"));

        existing.setUrl(webSecurityUrl.getUrl() != null ? webSecurityUrl.getUrl() : existing.getUrl());

        return repository.save(existing);
    }

    @Override
    public String getBranchCodeByUrl(String url) {
        WebSecurityUrl entity = repository.findByUrl(url)
                .orElseThrow(() -> new RuntimeException("URL not found: " + url));
        return entity.getBranchCode();
    }

}