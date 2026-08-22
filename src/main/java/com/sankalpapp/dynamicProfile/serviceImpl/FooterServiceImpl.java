package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebFooter;
import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.dynamicProfile.repository.FooterRepository;
import com.sankalpapp.dynamicProfile.repository.SecurityUrlrepository;
import com.sankalpapp.dynamicProfile.service.FooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FooterServiceImpl implements FooterService {

    @Autowired
    private FooterRepository repository;

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
        if (url == null) return "";
        return url.split(",")[0].trim().toLowerCase();
    }
    @Override
    public WebFooter createFooter(WebFooter webFooter, String url) {
         String normalizedUrl = normalizeUrl(url);
        WebSecurityUrl webSecurityUrl = securityUrlRepository.findByUrl(normalizedUrl)
                .orElseThrow(() -> new ResourceNotFoundException("Provided URL does not exist in security URL table"));

        webFooter.setUrl(url);
        webFooter.setWebSecurityUrl(webSecurityUrl);

        return repository.save(webFooter);
    }


    @Override
    public List<WebFooter> getAllFootersByBranchCode(String url) {
         return repository.findAllOrderById();
    }


    @Override
    public WebFooter updateFooter(Long id, WebFooter webFooter, String url) {
        //validateUrlExists;
        WebFooter existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Footer not found"));

        existing.setTitle(webFooter.getTitle() != null ? webFooter.getTitle() : existing.getTitle());
        existing.setFooterColor(webFooter.getFooterColor() != null ? webFooter.getFooterColor() : existing.getFooterColor());
        existing.setInstagramLink(webFooter.getInstagramLink() != null ? webFooter.getInstagramLink() : existing.getInstagramLink());
        existing.setFacebookLink(webFooter.getFacebookLink() != null ? webFooter.getFacebookLink() : existing.getFacebookLink());
        existing.setTwitterLink(webFooter.getTwitterLink() != null ? webFooter.getTwitterLink() : existing.getTwitterLink());
        existing.setYoutubeLink(webFooter.getYoutubeLink() != null ? webFooter.getYoutubeLink() : existing.getYoutubeLink());
        existing.setWhatsappLink(webFooter.getWhatsappLink() != null ? webFooter.getWhatsappLink() : existing.getWhatsappLink());
        existing.setEmail(webFooter.getEmail() != null ? webFooter.getEmail() : existing.getEmail());
        existing.setMobileNumber(webFooter.getMobileNumber() != null ? webFooter.getMobileNumber() : existing.getMobileNumber());
        existing.setAddress(webFooter.getAddress() != null ? webFooter.getAddress() : existing.getAddress());
        existing.setUrl(url != null ? url : existing.getUrl());

        return repository.save(existing);
    }

    @Override
    public void deleteFooter(Long id, String url) {
         repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Footer not found"));

        repository.deleteById(id);
    }

    @Override
    public WebFooter getFooterById(Long id, String url) {
         return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Footer not found"));
    }
}
