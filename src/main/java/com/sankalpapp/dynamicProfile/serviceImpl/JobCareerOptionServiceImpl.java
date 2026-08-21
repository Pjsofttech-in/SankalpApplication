package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.dto.WebJobCareerOptionDTO;
import com.sankalpapp.dynamicProfile.entity.WebHRDetails;
import com.sankalpapp.dynamicProfile.entity.WebJobCareerOption;
import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.dynamicProfile.repository.JobCareerOptionRepository;
import com.sankalpapp.dynamicProfile.repository.SecurityUrlrepository;
import com.sankalpapp.dynamicProfile.repository.WebHRDetailsRepository;
import com.sankalpapp.dynamicProfile.service.JobCareerOptionService;
import com.sankalpapp.dynamicProfile.service.PermissionService;
import com.sankalpapp.dynamicProfile.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class JobCareerOptionServiceImpl implements JobCareerOptionService {

    @Autowired
    private JobCareerOptionRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SecurityUrlrepository securityUrlRepository;

    @Autowired
    private WebHRDetailsRepository webHRDetailsRepository;

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
        return url == null ? "" : url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebJobCareerOptionDTO create(WebJobCareerOption option, String role, String email, MultipartFile resumeFile, String url, Long webHRDetailsId) {
        validateUrlExists(url,null);
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create job post");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        WebHRDetails webHRDetails = webHRDetailsRepository.findById(webHRDetailsId)
                .orElseThrow(() -> new ResourceNotFoundException("HR not found by id: " + webHRDetailsId));

        WebSecurityUrl webSecurityUrl = securityUrlRepository.findByUrl(normalizeUrl(url))
                .orElseThrow(() -> new ResourceNotFoundException("Provided URL does not exist"));

        List<WebJobCareerOption> existingJobs = repository.findAll();
        if (!existingJobs.isEmpty()) {
            option.setJobColour(existingJobs.get(0).getJobColour());
        }

        option.setRole(role);
        option.setCreatedByEmail(email);
        option.setBranchCode(branchCode);
        option.setUrl(url);
        option.setPostDate(LocalDate.now());
        option.setWebHRDetails(webHRDetails);
        option.setWebSecurityUrl(webSecurityUrl);

        if (resumeFile != null && !resumeFile.isEmpty()) {
            try {
                String uploadedUrl = s3Service.uploadImage(resumeFile, branchCode);
                option.setResumeUrl(uploadedUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload resume PDF to S3", e);
            }
        }

        return mapToDTO(repository.save(option));
    }

    @Override
    public WebJobCareerOptionDTO update(Long id, WebJobCareerOption option, String role, String email, MultipartFile resumeFile, String url, Long webHRDetailsId) {
        validateUrlExists(url,null);
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update job post");
        }

        WebJobCareerOption existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job post not found"));

        existing.setTitle(option.getTitle() != null ? option.getTitle() : existing.getTitle());
        existing.setDescription(option.getDescription() != null ? option.getDescription() : existing.getDescription());
        existing.setLocation(option.getLocation() != null ? option.getLocation() : existing.getLocation());
        existing.setSalaryRange(option.getSalaryRange() != null ? option.getSalaryRange() : existing.getSalaryRange());
        existing.setResponsibilities(option.getResponsibilities() != null ? option.getResponsibilities() : existing.getResponsibilities());
        existing.setPostDate(option.getPostDate() != null ? option.getPostDate() : existing.getPostDate());
        existing.setLastDateToApply(option.getLastDateToApply() != null ? option.getLastDateToApply() : existing.getLastDateToApply());
        existing.setUrl(option.getUrl() != null ? option.getUrl() : existing.getUrl());
        existing.setJobVacancy(option.getJobVacancy() != null ? option.getJobVacancy() : existing.getJobVacancy());

        String branchCode = permissionService.fetchBranchCode(role, email);

        if (resumeFile != null && !resumeFile.isEmpty()) {
            try {
                String uploadedUrl = s3Service.uploadImage(resumeFile, branchCode);
                existing.setResumeUrl(uploadedUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload resume to S3", e);
            }
        }


        if (webHRDetailsId != null) {
            WebHRDetails webHRDetails = webHRDetailsRepository.findById(webHRDetailsId)
                    .orElseThrow(() -> new ResourceNotFoundException("HR not found by id: " + webHRDetailsId));
            existing.setWebHRDetails(webHRDetails);
        }

        if (option.getJobColour() != null && !option.getJobColour().equals(existing.getJobColour())) {
            List<WebJobCareerOption> allJobs = repository.findAll();
            for (WebJobCareerOption job : allJobs) {
                job.setJobColour(option.getJobColour());
            }
            repository.saveAll(allJobs);
        }

        return mapToDTO(repository.save(existing));
    }



    @Override
    public List<WebJobCareerOptionDTO> getAllByBranchCode(String role, String email, String url, String branchCode) {
        validateUrlExists(url,branchCode);
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view job posts");
        }

        return repository.findAllByBranchCode(branchCode)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public WebJobCareerOptionDTO getById(Long id, String role, String email, String url) {
        validateUrlExists(url,null);
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view job post");
        }

        WebJobCareerOption job = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job post not found"));

        return mapToDTO(job);
    }

    private WebJobCareerOptionDTO mapToDTO(WebJobCareerOption option) {
        WebJobCareerOptionDTO dto = new WebJobCareerOptionDTO();
        dto.setId(option.getId());
        dto.setTitle(option.getTitle());
        dto.setDescription(option.getDescription());
        dto.setLocation(option.getLocation());
        dto.setSalaryRange(option.getSalaryRange());
        dto.setResponsibilities(option.getResponsibilities());
        dto.setPostDate(option.getPostDate());
        dto.setResumeUrl(option.getResumeUrl());
        dto.setLastDateToApply(option.getLastDateToApply());
        dto.setJobVacancy(option.getJobVacancy());
        dto.setUrl(option.getUrl());
        dto.setJobColour(option.getJobColour());
        dto.setCreatedByEmail(option.getCreatedByEmail());
        dto.setRole(option.getRole());
        dto.setBranchCode(option.getBranchCode());

        if (option.getWebHRDetails() != null) {
            dto.setWebHRDetailsId(option.getWebHRDetails().getId());
//            dto.setWebHRDetailsName(option.getWebHRDetails().getName()); // Replace with actual field
        }

        return dto;
    }

    @Override
    public void delete(Long id, String role, String email, String url) {
        validateUrlExists(url,null);

        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete job post");
        }

        WebJobCareerOption job = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job post not found with ID: " + id));

        repository.delete(job);
    }

}