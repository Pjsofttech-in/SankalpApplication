package com.testapplication.serviceimpl;

import com.testapplication.dto.Request.GalleryRequest;
import com.testapplication.dto.Response.GalleryResponse;
import com.testapplication.entity.Gallery;
import com.testapplication.repository.GalleryRepository;
import com.testapplication.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository repository;

    @Override
    public GalleryResponse saveGallery(GalleryRequest request) {

        Gallery gallery = Gallery.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .category(request.getCategory())
                .displayOrder(request.getDisplayOrder())
                .active(request.getActive())
                .build();

        return mapToResponse(repository.save(gallery));
    }

    @Override
    public GalleryResponse updateGallery(Long id, GalleryRequest request) {

        Gallery gallery = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gallery Not Found"));

        gallery.setTitle(request.getTitle());
        gallery.setDescription(request.getDescription());
        gallery.setImageUrl(request.getImageUrl());
        gallery.setCategory(request.getCategory());
        gallery.setDisplayOrder(request.getDisplayOrder());
        gallery.setActive(request.getActive());

        return mapToResponse(repository.save(gallery));
    }

    @Override
    public void deleteGallery(Long id) {

        Gallery gallery = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gallery Not Found"));

        repository.delete(gallery);
    }

    @Override
    public GalleryResponse getGalleryById(Long id) {

        Gallery gallery = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gallery Not Found"));

        return mapToResponse(gallery);
    }

    @Override
    public List<GalleryResponse> getAllGallery() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<GalleryResponse> getActiveGallery() {

        return repository.findByActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<GalleryResponse> getGalleryByCategory(String category) {

        return repository.findByCategoryIgnoreCase(category)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private GalleryResponse mapToResponse(Gallery gallery) {

        return GalleryResponse.builder()
                .id(gallery.getId())
                .title(gallery.getTitle())
                .description(gallery.getDescription())
                .imageUrl(gallery.getImageUrl())
                .category(gallery.getCategory())
                .displayOrder(gallery.getDisplayOrder())
                .active(gallery.getActive())
                .build();
    }
}