package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebMapAndImages;
import com.sankalpapp.dynamicProfile.repository.MapAndImagesRepository;
import com.sankalpapp.dynamicProfile.service.MapAndImagesService;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.serviceimpl.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MapAndImagesServiceImpl implements MapAndImagesService {

    @Autowired
    private MapAndImagesRepository repository;

    @Autowired
    private S3Service s3Service;

    private static final String folder = "MapAndImage";

    @Override
    public WebMapAndImages create(WebMapAndImages entity, MultipartFile imageFile, String url) {

        entity.setUrl(url);

        uploadFile(imageFile, entity);

        return repository.save(entity);
    }


    @Override
    public List<WebMapAndImages> getAllByBranchCode(String url) {
        return repository.findAllOrderById();
    }


    @Override
    public WebMapAndImages update(Long id, WebMapAndImages updated, MultipartFile imageFile, String url) {
        WebMapAndImages existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MapAndImages not found"));

        existing.setMaps(updated.getMaps() != null ? updated.getMaps() : existing.getMaps());
        existing.setUrl(updated.getUrl() != null ? updated.getUrl() : existing.getUrl());

        uploadFile(imageFile, existing);

        return repository.save(existing);
    }

    private void uploadFile(MultipartFile pdf, WebMapAndImages obj) {
        if (pdf != null) {
            try {
                String fileURL = s3Service.uploadFile(pdf, folder);
                obj.setContactImage(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
    }

    @Override
    public void delete(Long id, String url) {
        WebMapAndImages entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MapAndImages not found"));

        s3Service.deleteFileByUrl(entity.getContactImage());

        repository.deleteById(id);
    }

    @Override
    public WebMapAndImages getById(Long id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MapAndImages not found"));
    }
}