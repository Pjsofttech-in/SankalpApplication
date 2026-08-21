package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebMapAndImages;
import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.dynamicProfile.repository.MapAndImagesRepository;
import com.sankalpapp.dynamicProfile.repository.SecurityUrlrepository;
import com.sankalpapp.dynamicProfile.service.MapAndImagesService;
import com.sankalpapp.dynamicProfile.service.S3Service;
import com.sankalpapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class MapAndImagesServiceImpl implements MapAndImagesService {

    @Autowired
    private MapAndImagesRepository repository;

    @Autowired
    private SecurityUrlrepository securityUrlRepository;

    @Autowired
    private S3Service s3Service;

    private void validateUrlExists(String url) {

        String normalizedUrl = normalizeUrl(url);
        securityUrlRepository.findByUrl(normalizedUrl)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Provided URL [" + url + "] does not exist"
                ));
    }


    private String normalizeUrl(String url) {
        return (url == null) ? "" : url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebMapAndImages create(WebMapAndImages entity, MultipartFile imageFile, String url) {
        validateUrlExists(url);

        WebSecurityUrl webSecurityUrl = securityUrlRepository.findByUrl(normalizeUrl(url))
                .orElseThrow(() -> new ResourceNotFoundException("Provided URL does not exist in security URL table"));

        entity.setUrl(url);
        entity.setWebSecurityUrl(webSecurityUrl);

//        if (imageFile != null && !imageFile.isEmpty()) {
//            try {
//                String imageUrl = s3Service.uploadImage(imageFile);
//                entity.setContactImage(imageUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload contact image", e);
//            }
//        }

        return repository.save(entity);
    }


    @Override
    public List<WebMapAndImages> getAllByBranchCode(String url) {
        validateUrlExists(url);

        return repository.findAllOrderById();
    }


    @Override
    public WebMapAndImages update(Long id, WebMapAndImages updated, MultipartFile imageFile, String url) {
        validateUrlExists(url);

        WebMapAndImages existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MapAndImages not found"));

        existing.setMaps(updated.getMaps() != null ? updated.getMaps() : existing.getMaps());
        existing.setUrl(updated.getUrl() != null ? updated.getUrl() : existing.getUrl());

//        if (imageFile != null && !imageFile.isEmpty()) {
//            try {
//                String newImageUrl = s3Service.uploadImage(imageFile);
//
//                // Optional: delete old image if stored in S3
//                if (existing.getContactImage() != null && existing.getContactImage().contains("amazonaws.com")) {
//                    s3Service.deleteImage(existing.getContactImage());
//                }
//
//                existing.setContactImage(newImageUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload contact image", e);
//            }
//        }

        return repository.save(existing);
    }

    @Override
    public void delete(Long id, String url) {
        validateUrlExists(url);

        WebMapAndImages entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MapAndImages not found"));

        if (entity.getContactImage() != null && entity.getContactImage().contains("amazonaws.com")) {
            s3Service.deleteImage(entity.getContactImage());
        }

        repository.deleteById(id);
    }

    @Override
    public WebMapAndImages getById(Long id, String url) {
        validateUrlExists(url);

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MapAndImages not found"));
    }
}