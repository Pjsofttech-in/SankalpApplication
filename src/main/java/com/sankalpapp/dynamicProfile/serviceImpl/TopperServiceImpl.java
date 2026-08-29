package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebTopper;
import com.sankalpapp.dynamicProfile.repository.TopperRepository;
import com.sankalpapp.dynamicProfile.service.TopperService;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.serviceimpl.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TopperServiceImpl implements TopperService {

    private static final String folder = "Topper";
    @Autowired
    private TopperRepository repository;
    @Autowired
    private S3Service s3Service;

    @Override
    public WebTopper createTopper(WebTopper webTopper, MultipartFile topperImage, String url) {
        // Apply static color from first record if exists
        List<WebTopper> existingWebToppers = repository.findAll();
        if (!existingWebToppers.isEmpty()) {
            webTopper.setTopperColor(existingWebToppers.getFirst().getTopperColor());
        }

        webTopper.setUrl(url);

        uploadFile(topperImage, webTopper);

        return repository.save(webTopper);
    }

    private void uploadFile(MultipartFile pdf, WebTopper obj) {
        if (pdf != null) {
            try {
                String fileURL = s3Service.uploadFile(pdf, folder);
                obj.setTopperImage(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
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

        uploadFile(topperImage, existing);

        return repository.save(existing);
    }

    @Override
    public void deleteTopper(Long id, String url) {
        WebTopper webTopper = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topper not found"));

        s3Service.deleteFileByUrl(webTopper.getTopperImage());

        repository.deleteById(id);
    }

    @Override
    public WebTopper getTopperById(Long id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topper not found"));
    }
}