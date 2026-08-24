package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebFooter;
import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.dynamicProfile.repository.FooterRepository;
import com.sankalpapp.dynamicProfile.service.FooterService;
import com.sankalpapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FooterServiceImpl implements FooterService {

    @Autowired
    private FooterRepository repository;

    @Override
    public WebFooter createFooter(WebFooter webFooter, String url) {
        webFooter.setUrl(url);
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
