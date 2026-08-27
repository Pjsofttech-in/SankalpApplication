package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.dto.WebJobCareerOptionDTO;
import com.sankalpapp.dynamicProfile.entity.WebHRDetails;
import com.sankalpapp.dynamicProfile.entity.WebJobCareerOption;
import com.sankalpapp.dynamicProfile.repository.JobCareerOptionRepository;
import com.sankalpapp.dynamicProfile.repository.WebHRDetailsRepository;
import com.sankalpapp.dynamicProfile.service.JobCareerOptionService;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.serviceimpl.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
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
    private WebHRDetailsRepository webHRDetailsRepository;

    @Autowired
    private S3Service s3Service;

    private static final String folder = "JobCareerOption";

    @Override
    public WebJobCareerOptionDTO create(WebJobCareerOption option, MultipartFile resumeFile, String url, Long webHRDetailsId) {
        WebHRDetails webHRDetails = webHRDetailsRepository.findById(webHRDetailsId)
                .orElseThrow(() -> new ResourceNotFoundException("HR not found by id: " + webHRDetailsId));

        List<WebJobCareerOption> existingJobs = repository.findAll();
        if (!existingJobs.isEmpty()) {
            option.setJobColour(existingJobs.get(0).getJobColour());
        }

        option.setUrl(url);
        option.setPostDate(LocalDate.now());
        option.setWebHRDetails(webHRDetails);

        uploadFile(resumeFile, option);

        return mapToDTO(repository.save(option));
    }

    private void uploadFile(MultipartFile pdf, WebJobCareerOption obj) {
        if (pdf != null) {
            try {
                String fileURL = s3Service.uploadFile(pdf, folder);
                obj.setResumeUrl(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
    }

    @Override
    public WebJobCareerOptionDTO update(Long id, WebJobCareerOption option, MultipartFile resumeFile, String url, Long webHRDetailsId) {
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

        uploadFile(resumeFile, existing);


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
    public List<WebJobCareerOptionDTO> getAllByBranchCode(String url) {
        return repository.findAllOrderById()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public WebJobCareerOptionDTO getById(Long id, String url) {
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

        if (option.getWebHRDetails() != null) {
            dto.setWebHRDetailsId(option.getWebHRDetails().getId());
//            dto.setWebHRDetailsName(option.getWebHRDetails().getName()); // Replace with actual field
        }

        return dto;
    }

    @Override
    public void delete(Long id, String url) {
        WebJobCareerOption job = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job post not found with ID: " + id));

        s3Service.deleteFileByUrl(job.getResumeUrl());
        repository.delete(job);
    }

}