package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebFacultyTitle;
import com.sankalpapp.dynamicProfile.repository.FacultyTitleRepository;
import com.sankalpapp.dynamicProfile.service.FacultyTitleService;
import com.sankalpapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyTitleServiceImpl implements FacultyTitleService {

    @Autowired
    private FacultyTitleRepository repository;

    private String normalizeUrl(String url) {
        return url == null ? "" : url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebFacultyTitle createFacilityTitle(WebFacultyTitle webFacultyTitle, String url) {
        webFacultyTitle.setUrl(url);

        return repository.save(webFacultyTitle);
    }

    @Override
    public List<WebFacultyTitle> getAllFacilityTitlesByBranchCode(String url) {
        return repository.findAllOrderById();
    }


    @Override
    public WebFacultyTitle updateFacilityTitle(Long id, WebFacultyTitle updated, String url) {
        WebFacultyTitle existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FacilityTitle not found"));

        existing.setFacilityTitle(updated.getFacilityTitle() != null ? updated.getFacilityTitle() : existing.getFacilityTitle());

        return repository.save(existing);
    }

    @Override
    public void deleteFacilityTitle(Long id, String url) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("FacilityTitle not found"));
        repository.deleteById(id);
    }

    @Override
    public WebFacultyTitle getFacilityTitleById(Long id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FacilityTitle not found"));
    }
}