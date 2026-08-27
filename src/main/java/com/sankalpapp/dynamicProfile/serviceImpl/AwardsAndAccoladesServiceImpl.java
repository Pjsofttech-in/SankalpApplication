package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebAwardsAndAccolades;
import com.sankalpapp.dynamicProfile.repository.AwardsAndAccoladesRepository;
import com.sankalpapp.dynamicProfile.service.AwardsAndAccoladesService;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.serviceimpl.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AwardsAndAccoladesServiceImpl implements AwardsAndAccoladesService {

    @Autowired
    private AwardsAndAccoladesRepository repository;

    @Autowired
    private S3Service s3Service;

    private static final String folder = "Awards";

    @Override
    public WebAwardsAndAccolades createAward(WebAwardsAndAccolades award, MultipartFile awardImage, String url) {
        // Static color logic: Use color from first record if exists
        List<WebAwardsAndAccolades> existing = repository.findAll();
        if (!existing.isEmpty()) {
            award.setAwardColour(existing.get(0).getAwardColour());
        }
        award.setUrl(url);
        uploadFile(awardImage, award);

        return repository.save(award);
    }

    private void uploadFile(MultipartFile pdf, WebAwardsAndAccolades obj) {
        if (pdf != null) {
            try {
                String fileURL = s3Service.uploadFile(pdf, folder);
                obj.setAwardImage(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
    }


    @Override
    public List<WebAwardsAndAccolades> getAllAwardsByBranchCode(String url) {
        //validateUrlExists;
        return repository.findAllOrderById();
    }


    @Override
    public WebAwardsAndAccolades updateAward(Long id, WebAwardsAndAccolades award, MultipartFile awardImage, String url) {
        WebAwardsAndAccolades existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Award not found"));

        existing.setAwardName(award.getAwardName() != null ? award.getAwardName() : existing.getAwardName());
        existing.setDescription(award.getDescription() != null ? award.getDescription() : existing.getDescription());
        existing.setAwardedBy(award.getAwardedBy() != null ? award.getAwardedBy() : existing.getAwardedBy());
        existing.setYear(award.getYear() != 0 ? award.getYear() : existing.getYear());
        existing.setAwardTo(award.getAwardTo() != null ? award.getAwardTo() : existing.getAwardTo());
        existing.setUrl(award.getUrl() != null ? award.getUrl() : existing.getUrl());

        // Static color logic: If color changed, update all
        if (award.getAwardColour() != null && !award.getAwardColour().equals(existing.getAwardColour())) {
            List<WebAwardsAndAccolades> allAwards = repository.findAll();
            for (WebAwardsAndAccolades a : allAwards) {
                a.setAwardColour(award.getAwardColour());
            }
            repository.saveAll(allAwards);
        }

        uploadFile(awardImage, existing);
        return repository.save(existing);
    }


    @Override
    public void deleteAward(Long id, String url) {
        WebAwardsAndAccolades award = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Award not found"));

        s3Service.deleteFileByUrl(award.getAwardImage());

        repository.deleteById(id);
    }

    @Override
    public WebAwardsAndAccolades getAwardById(Long id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Award not found"));
    }
}
