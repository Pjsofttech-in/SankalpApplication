package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebGallery;
import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.dynamicProfile.repository.GalleryRepository;
import com.sankalpapp.dynamicProfile.repository.SecurityUrlrepository;
import com.sankalpapp.dynamicProfile.service.GalleryService;
import com.sankalpapp.dynamicProfile.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GalleryServiceImpl implements GalleryService {

    @Autowired
    private GalleryRepository repository;

    @Autowired
    private SecurityUrlrepository securityUrlRepository;

    @Autowired
    private S3Service s3Service;

    private void validateUrlExists(String url) {

        String normalizedUrl = normalizeUrl(url);
        securityUrlRepository.findByUrl(normalizedUrl)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "URL [" + url + "] is not allowed for branchCode "
                ));
    }


    private String normalizeUrl(String url) {
        return (url == null) ? "" : url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebGallery createGallery(WebGallery webGallery, List<MultipartFile> images, String url) {
         WebSecurityUrl webSecurityUrl = securityUrlRepository.findByUrl(normalizeUrl(url))
                .orElseThrow(() -> new ResourceNotFoundException("Security URL not found"));

        webGallery.setUrl(url);
        webGallery.setWebSecurityUrl(webSecurityUrl);

        // Static color logic
        List<WebGallery> existing = repository.findAll();
        if (!existing.isEmpty()) {
            webGallery.setGalleryColor(existing.get(0).getGalleryColor());
        }

        // Upload images to S3
//        List<String> uploaded = new ArrayList<>();
//        if (images != null) {
//            for (MultipartFile image : images) {
//                try {
//                    String imageUrl = s3Service.uploadImage(image);
//                    uploaded.add(imageUrl);
//                } catch (IOException e) {
//                    throw new RuntimeException("Image upload failed", e);
//                }
//            }
//        }
//        webGallery.setGalleryImages(uploaded);

        return repository.save(webGallery);
    }


    @Override
    public List<WebGallery> getAllGalleriesByBranchCode(String url) {
        //validateUrlExists;
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
            currentImages.removeIf(imageUrl -> {
                String fileName = extractFileName(imageUrl);
                if (deleteImages.contains(fileName)) {
                    s3Service.deleteImage(imageUrl);
                    return true;
                }
                return false;
            });
        }

//        if (newImages != null) {
//            String branchCode = permissionService.fetchBranchCode(role, email);
//            for (MultipartFile image : newImages) {
//                try {
//                    String imageUrl = s3Service.uploadImage(image);
//                    currentImages.add(imageUrl);
//                } catch (IOException e) {
//                    throw new RuntimeException("Image upload failed", e);
//                }
//            }
//        }

        existing.setGalleryImages(currentImages);
        return repository.save(existing);
    }


    private String extractFileName(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }

    @Override
    public void deleteGallery(Long id, String url) {
         WebGallery webGallery = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gallery not found"));

        if (webGallery.getGalleryImages() != null) {
            for (String image : webGallery.getGalleryImages()) {
                s3Service.deleteImage(image);
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