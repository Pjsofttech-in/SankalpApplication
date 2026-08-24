package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebGallery;
import com.sankalpapp.dynamicProfile.repository.GalleryRepository;
import com.sankalpapp.dynamicProfile.service.GalleryService;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.serviceimpl.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GalleryServiceImpl implements GalleryService {

    private static final String folder = "Gallery";
    @Autowired
    private GalleryRepository repository;
    @Autowired
    private S3Service s3Service;

    @Override
    public WebGallery createGallery(WebGallery webGallery, List<MultipartFile> images, String url) {
        webGallery.setUrl(url);

        // Static color logic
        List<WebGallery> existing = repository.findAll();
        if (!existing.isEmpty()) {
            webGallery.setGalleryColor(existing.get(0).getGalleryColor());
        }
        uploadFile(images, webGallery);
        return repository.save(webGallery);
    }

    private void uploadFile(List<MultipartFile> images, WebGallery obj) {
        if (!CollectionUtils.isEmpty(images)) {
            List<String> urls = new ArrayList<>();
            for (MultipartFile image : images) {
                try {
                    String fileURL = s3Service.uploadFile(image, folder);
                    urls.add(fileURL);
                } catch (IOException e) {
                    throw new RuntimeException("Unable to upload File");
                }
            }
            obj.setGalleryImages(urls);
        }
    }


    @Override
    public List<WebGallery> getAllGalleriesByBranchCode(String url) {
        return repository.findAllOrderById();
    }


    @Override
    public WebGallery updateGallery(Long id, WebGallery webGallery,
                                    List<MultipartFile> newImages, List<String> deleteImages, String url) {
        //validateUrlExists;
        WebGallery existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gallery not found"));

        if (webGallery.getEventName() != null) existing.setEventName(webGallery.getEventName());
        if (webGallery.getYear() != null) existing.setYear(webGallery.getYear());
        if (webGallery.getUrl() != null) existing.setUrl(webGallery.getUrl());
        if (webGallery.getTitle() != null) existing.setTitle(webGallery.getTitle());

        // Static color update logic
        if (webGallery.getGalleryColor() != null && !webGallery.getGalleryColor().equals(existing.getGalleryColor())) {
            List<WebGallery> all = repository.findAll();
            for (WebGallery g : all) {
                g.setGalleryColor(webGallery.getGalleryColor());
            }
            repository.saveAll(all); // Update color for all galleries
            existing.setGalleryColor(webGallery.getGalleryColor()); // Optional: update again if not picked above
        }

        List<String> currentImages = new ArrayList<>(Optional.ofNullable(existing.getGalleryImages()).orElse(new ArrayList<>()));

        if (deleteImages != null) {
            currentImages.forEach(imageUrl -> {
                s3Service.deleteFileByUrl(imageUrl);
            });
        }
        uploadFile(newImages, existing);
        return repository.save(existing);
    }

    @Override
    public void deleteGallery(Long id, String url) {
        WebGallery webGallery = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gallery not found"));

        if (webGallery.getGalleryImages() != null) {
            for (String image : webGallery.getGalleryImages()) {
                s3Service.deleteFileByUrl(image);
            }
        }

        repository.deleteById(id);
    }

    @Override
    public WebGallery getGalleryById(Long id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gallery not found"));
    }
}