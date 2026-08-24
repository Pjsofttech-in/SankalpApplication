package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebSlideBar;
import com.sankalpapp.dynamicProfile.repository.SlideBarRepository;
import com.sankalpapp.dynamicProfile.service.SlideBarService;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.serviceimpl.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class SlideBarServiceImpl implements SlideBarService {

    @Autowired
    private SlideBarRepository repository;

    @Autowired
    private S3Service s3Service;

    @Override
    public WebSlideBar createSlideBar(WebSlideBar webSlideBar, List<MultipartFile> slideBarImages, String url) {

        webSlideBar.setUrl(url);

//        List<String> uploadedUrls = new ArrayList<>();
//        if (slideBarImages != null && !slideBarImages.isEmpty()) {
//            for (MultipartFile imageFile : slideBarImages) {
//                if (!imageFile.isEmpty()) {
//                    try {
//                        String imageUrl = s3Service.uploadImage(imageFile);
//                        uploadedUrls.add(imageUrl);
//                    } catch (IOException e) {
//                        throw new RuntimeException("Failed to upload slide bar image", e);
//                    }
//                }
//            }
//        }

//        webSlideBar.setSlideImages(uploadedUrls); // ✅ ensure it's a mutable list

        return repository.save(webSlideBar);
    }


    @Override
    public List<WebSlideBar> getAllByBranchCode(String url) {
        return repository.findAllOrderById();
    }


    @Override
    public WebSlideBar updateSlideBar(Long id, WebSlideBar webSlideBar,
                                      List<MultipartFile> newImages, List<String> deleteImages, String url) {
        WebSlideBar existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SlideBar not found"));

        // Update optional fields
        if (webSlideBar != null) {
            if (webSlideBar.getSlideBarColor() != null) {
                existing.setSlideBarColor(webSlideBar.getSlideBarColor());
            }
            if (webSlideBar.getUrl() != null) {
                existing.setUrl(webSlideBar.getUrl());
            }

        }

        // Make sure current slide images list is mutable
        List<String> currentImages = existing.getSlideImages() != null
                ? new ArrayList<>(existing.getSlideImages())
                : new ArrayList<>();

        // Delete matching images by filename
//        if (!currentImages.isEmpty() && deleteImages != null && !deleteImages.isEmpty()) {
//            Iterator<String> iterator = currentImages.iterator();
//            while (iterator.hasNext()) {
//                String existingImageUrl = iterator.next();
//                String existingImageName = extractFileName(existingImageUrl);
//                if (deleteImages.contains(existingImageName)) {
//                    if (existingImageUrl.contains("amazonaws.com")) {
//                        s3Service.deleteImage(existingImageUrl);
//                    }
//                    iterator.remove();
//                }
//            }
//            existing.setSlideImages(currentImages);
//        }

        // Upload and add new images
//        if (newImages != null && !newImages.isEmpty()) {
//            for (MultipartFile imageFile : newImages) {
//                if (!imageFile.isEmpty()) {
//                    try {
//                        String imageUrl = s3Service.uploadImage(imageFile);
//                        currentImages.add(imageUrl);
//                    } catch (IOException e) {
//                        throw new RuntimeException("Failed to upload slide bar image", e);
//                    }
//                }
//            }
//            existing.setSlideImages(currentImages); // update list after addition
//        }

        return repository.save(existing);
    }

    private String extractFileName(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) return "";
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }


    @Override
    public void deleteSlideBar(Long id, String url) {
        WebSlideBar webSlideBar = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SlideBar not found"));

//        // ✅ Delete all images
//        if (webSlideBar.getSlideImages() != null) {
//            for (String image : webSlideBar.getSlideImages()) {
//                if (image != null && image.contains("amazonaws.com")) {
//                    s3Service.deleteImage(image);
//                }
//            }
//        }

        repository.deleteById(id);
    }


    @Override
    public WebSlideBar getSlideBarById(Long id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SlideBar not found"));
    }
}