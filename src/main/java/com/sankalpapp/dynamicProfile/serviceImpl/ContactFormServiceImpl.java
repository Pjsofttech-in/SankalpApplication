package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebContactForm;
import com.sankalpapp.dynamicProfile.repository.ContactFormRepository;
import com.sankalpapp.dynamicProfile.service.ContactFormService;
import com.sankalpapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactFormServiceImpl implements ContactFormService {

    @Autowired
    private ContactFormRepository repository;

    private String normalizeUrl(String url) {
        return url == null ? "" : url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebContactForm create(WebContactForm webContactForm, String urlFromRequest) {
        webContactForm.setUrl(urlFromRequest);

        return repository.save(webContactForm);
    }


    @Override
    public List<WebContactForm> getAllByBranchCode(String url) {
        return repository.findAllOrderById();
    }


    @Override
    public WebContactForm update(Long id, WebContactForm webContactForm, String url) {
        WebContactForm existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactForm not found"));

        existing.setName(webContactForm.getName() != null ? webContactForm.getName() : existing.getName());
        existing.setMobileNo(webContactForm.getMobileNo() != null ? webContactForm.getMobileNo() : existing.getMobileNo());
        existing.setCourse(webContactForm.getCourse() != null ? webContactForm.getCourse() : existing.getCourse());
        existing.setSubject(webContactForm.getSubject() != null ? webContactForm.getSubject() : existing.getSubject());
        existing.setDescription(webContactForm.getDescription() != null ? webContactForm.getDescription() : existing.getDescription());
        existing.setAcademicYear(webContactForm.getAcademicYear() != null ? webContactForm.getAcademicYear() : existing.getAcademicYear());
        existing.setEmail(webContactForm.getEmail() != null ? webContactForm.getEmail() : existing.getEmail());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id, String url) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ContactForm not found"));
        repository.deleteById(id);
    }

    @Override
    public WebContactForm getById(Long id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactForm not found"));
    }
}