package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebFaculty;
import com.sankalpapp.dynamicProfile.repository.FacultyRepository;
import com.sankalpapp.dynamicProfile.service.FacultyService;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.serviceimpl.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private FacultyRepository repository;

    @Autowired
    private S3Service s3Service;

    private static final String folder = "Faculty";

    @Override
    public WebFaculty createFacility(WebFaculty webFaculty, MultipartFile image, String url) {
        // Apply static color logic
        List<WebFaculty> existing = repository.findAll();
        if (!existing.isEmpty()) {
            webFaculty.setFacilityColor(existing.getFirst().getFacilityColor());
        }

        webFaculty.setUrl(url);

        uploadFile(image, webFaculty);

        return repository.save(webFaculty);
    }

    private void uploadFile(MultipartFile pdf, WebFaculty obj) {
        if (pdf != null) {
            try {
                String fileURL = s3Service.uploadFile(pdf, folder);
                obj.setFacilityImage(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
    }


    @Override
    public List<WebFaculty> getAllFacilitiesByBranchCode(String url) {
        //validateUrlExists;
        return repository.findAllOrderById();
    }


    @Override
    public WebFaculty updateFacility(Long id, WebFaculty webFaculty, MultipartFile image, String url) {
        WebFaculty existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found"));

        existing.setFacilityName(webFaculty.getFacilityName() != null ? webFaculty.getFacilityName() : existing.getFacilityName());
        existing.setSubject(webFaculty.getSubject() != null ? webFaculty.getSubject() : existing.getSubject());
        existing.setFacilityEducation(webFaculty.getFacilityEducation() != null ? webFaculty.getFacilityEducation() : existing.getFacilityEducation());
        existing.setDescription(webFaculty.getDescription() != null ? webFaculty.getDescription() : existing.getDescription());
        existing.setExperienceInYear(webFaculty.getExperienceInYear() != null ? webFaculty.getExperienceInYear() : existing.getExperienceInYear());

        // Static color update logic
        if (webFaculty.getFacilityColor() != null && !webFaculty.getFacilityColor().equals(existing.getFacilityColor())) {
            List<WebFaculty> allFacilities = repository.findAll();
            for (WebFaculty f : allFacilities) {
                f.setFacilityColor(webFaculty.getFacilityColor());
            }
            repository.saveAll(allFacilities);
        }

        uploadFile(image, webFaculty);

        return repository.save(existing);
    }


    @Override
    public void deleteFacility(Long id, String url) {
        WebFaculty webFaculty = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found"));

        s3Service.deleteFileByUrl(webFaculty.getFacilityImage());

        repository.deleteById(id);
    }

    @Override
    public WebFaculty getFacilityById(Long id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found"));
    }
}