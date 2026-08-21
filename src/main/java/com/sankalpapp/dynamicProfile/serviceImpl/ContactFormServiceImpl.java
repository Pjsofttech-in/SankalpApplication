package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebContactForm;
import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.dynamicProfile.repository.ContactFormRepository;
import com.sankalpapp.dynamicProfile.repository.SecurityUrlrepository;
import com.sankalpapp.dynamicProfile.service.ContactFormService;
import com.sankalpapp.dynamicProfile.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactFormServiceImpl implements ContactFormService {

    @Autowired
    private ContactFormRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SecurityUrlrepository securityUrlRepository;

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
        return url == null ? "" : url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebContactForm create(WebContactForm webContactForm, String role, String email, String url, String branchCodeFromRequest) {
        validateUrlExists(url,null);

        role = role.trim().toUpperCase();
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create contact form");
        }

        String branchCode;
        if ("USER".equals(role)) {
            if (branchCodeFromRequest == null || branchCodeFromRequest.isBlank()) {
                throw new IllegalArgumentException("BranchCode must be provided for USER role");
            }
            branchCode = branchCodeFromRequest;
        } else {
            branchCode = permissionService.fetchBranchCode(role, email);
        }

        WebSecurityUrl webSecurityUrl = securityUrlRepository.findByUrl(normalizeUrl(url))
                .orElseThrow(() -> new ResourceNotFoundException("Provided URL does not exist in security URL table"));

        webContactForm.setRole(role);
        webContactForm.setCreatedByEmail(email);
        webContactForm.setBranchCode(branchCode);
        webContactForm.setWebSecurityUrl(webSecurityUrl);
        webContactForm.setUrl(url);

        return repository.save(webContactForm);
    }



    @Override
    public List<WebContactForm> getAllByBranchCode(String role, String email, String url, String branchCode) {
        validateUrlExists(url,branchCode);
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view contact forms");
        }

        return repository.findAllByBranchCode(branchCode);
    }


    @Override
    public WebContactForm update(Long id, WebContactForm webContactForm, String role, String email, String url) {
        validateUrlExists(url,null);
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update contact form");
        }

        WebContactForm existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactForm not found"));

        existing.setName(webContactForm.getName() != null ? webContactForm.getName() : existing.getName());
        existing.setMobileNo(webContactForm.getMobileNo() != null ? webContactForm.getMobileNo() : existing.getMobileNo());
        existing.setCourse(webContactForm.getCourse() != null ? webContactForm.getCourse() : existing.getCourse());
        existing.setDescription(webContactForm.getDescription() != null ? webContactForm.getDescription() : existing.getDescription());
        existing.setAcademicYear(webContactForm.getAcademicYear() != null ? webContactForm.getAcademicYear() : existing.getAcademicYear());
        existing.setEmail(webContactForm.getEmail() != null ? webContactForm.getEmail() : existing.getEmail());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id, String role, String email, String url) {
        validateUrlExists(url,null);
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete contact form");
        }

        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ContactForm not found"));
        repository.deleteById(id);
    }

    @Override
    public WebContactForm getById(Long id, String role, String email, String url) {
        validateUrlExists(url,null);
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view contact form");
        }

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactForm not found"));
    }
}