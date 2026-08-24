package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.dynamicProfile.entity.WebTopper;
import com.sankalpapp.dynamicProfile.repository.TopperRepository;
import com.sankalpapp.dynamicProfile.service.S3Service;
import com.sankalpapp.dynamicProfile.service.TopperService;
import com.sankalpapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TopperServiceImpl implements TopperService {

    @Autowired
    private TopperRepository repository;

    @Autowired
    private S3Service s3Service;

    private String normalizeUrl(String url) {
        return (url == null) ? "" : url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebTopper createTopper(WebTopper webTopper, MultipartFile topperImage, String url) {
        // Apply static color from first record if exists
        List<WebTopper> existingWebToppers = repository.findAll();
        if (!existingWebToppers.isEmpty()) {
            webTopper.setTopperColor(existingWebToppers.get(0).getTopperColor());
        }

        webTopper.setUrl(url);

//        if (topperImage != null && !topperImage.isEmpty()) {
//            try {
//                String imageUrl = s3Service.uploadImage(topperImage);
//                webTopper.setTopperImage(imageUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload Topper image", e);
//            }
//        }

        return repository.save(webTopper);
    }


    @Override
    public List<WebTopper> getAllToppersByBranchCode(String url) {
        return repository.findAllOrderById();
    }

    @Override
    public WebTopper updateTopper(Long id, WebTopper updatedWebTopper, MultipartFile topperImage, String url) {
        WebTopper existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topper not found"));

        existing.setName(updatedWebTopper.getName() != null ? updatedWebTopper.getName() : existing.getName());
        existing.setClassName(updatedWebTopper.getClassName() != null ? updatedWebTopper.getClassName() : existing.getClassName());
        existing.setPost(updatedWebTopper.getPost() != null ? updatedWebTopper.getPost() : existing.getPost());
        existing.setTotalMarks(updatedWebTopper.getTotalMarks() != null ? updatedWebTopper.getTotalMarks() : existing.getTotalMarks());
        existing.setRank(updatedWebTopper.getRank() != null ? updatedWebTopper.getRank() : existing.getRank());
        existing.setYear(updatedWebTopper.getYear() != null ? updatedWebTopper.getYear() : existing.getYear());
        existing.setTopperImages(updatedWebTopper.getTopperImages() != null ? updatedWebTopper.getTopperImages() : existing.getTopperImages());
        existing.setImageUrlIds(updatedWebTopper.getImageUrlIds() != null ? updatedWebTopper.getImageUrlIds() : existing.getImageUrlIds());

//        String branchCode = permissionService.fetchBranchCode(role, email);

        // Update all if color is changed
        if (updatedWebTopper.getTopperColor() != null && !updatedWebTopper.getTopperColor().equals(existing.getTopperColor())) {
            List<WebTopper> allWebToppers = repository.findAll();
            for (WebTopper webTopper : allWebToppers) {
                webTopper.setTopperColor(updatedWebTopper.getTopperColor());
            }
            repository.saveAll(allWebToppers); // Save all updated
        }

        if (topperImage != null && !topperImage.isEmpty()) {
//            try {
//                String imageUrl = s3Service.uploadImage(topperImage);
//
//                if (existing.getTopperImage() != null && existing.getTopperImage().contains("amazonaws.com")) {
//                    s3Service.deleteImage(existing.getTopperImage());
//                }
//
//                existing.setTopperImage(imageUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload Topper image", e);
//            }
        }

        return repository.save(existing);
    }

    @Override
    public void deleteTopper(Long id, String url) {
        WebTopper webTopper = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topper not found"));

        if (webTopper.getTopperImage() != null && webTopper.getTopperImage().contains("amazonaws.com")) {
            s3Service.deleteImage(webTopper.getTopperImage());
        }

        repository.deleteById(id);
    }

    @Override
    public WebTopper getTopperById(Long id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topper not found"));
    }
}