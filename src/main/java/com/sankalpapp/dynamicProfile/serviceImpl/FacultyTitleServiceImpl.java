package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebFacultyTitle;
import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.dynamicProfile.repository.FacultyTitleRepository;
import com.sankalpapp.dynamicProfile.repository.SecurityUrlrepository;
import com.sankalpapp.dynamicProfile.service.FacultyTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyTitleServiceImpl implements FacultyTitleService {

    @Autowired
    private FacultyTitleRepository repository;

    @Autowired
    private SecurityUrlrepository securityUrlRepository;

    private void validateUrlExists(String url) {

        String normalizedUrl = normalizeUrl(url);

        securityUrlRepository.findByUrl(normalizedUrl)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "URL [" + url + "] is not allowed for branchCode"
                ));
    }


    private String normalizeUrl(String url) {
        return url == null ? "" : url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebFacultyTitle createFacilityTitle(WebFacultyTitle webFacultyTitle, String url) {
        validateUrlExists(url);
        WebSecurityUrl webSecurityUrl = securityUrlRepository.findByUrl(normalizeUrl(url))
                .orElseThrow(() -> new ResourceNotFoundException("Provided URL does not exist in security URL table"));

        webFacultyTitle.setWebSecurityUrl(webSecurityUrl);
        webFacultyTitle.setUrl(url);

        return repository.save(webFacultyTitle);
    }

    @Override
    public List<WebFacultyTitle> getAllFacilityTitlesByBranchCode(String url) {
        validateUrlExists(url);
        return repository.findAllOrderById();
    }


    @Override
    public WebFacultyTitle updateFacilityTitle(Long id, WebFacultyTitle updated, String url) {
        validateUrlExists(url);

        WebFacultyTitle existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FacilityTitle not found"));

        existing.setFacilityTitle(updated.getFacilityTitle() != null ? updated.getFacilityTitle() : existing.getFacilityTitle());

        return repository.save(existing);
    }

    @Override
    public void deleteFacilityTitle(Long id, String url) {
        validateUrlExists(url);

        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("FacilityTitle not found"));
        repository.deleteById(id);
    }

    @Override
    public WebFacultyTitle getFacilityTitleById(Long id, String url) {
        validateUrlExists(url);

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FacilityTitle not found"));
    }
}