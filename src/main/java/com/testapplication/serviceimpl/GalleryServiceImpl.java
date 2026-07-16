package com.testapplication.serviceimpl;

import com.testapplication.entity.Gallery;
import com.testapplication.repository.GalleryRepository;
import com.testapplication.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository repository;

    @Override
    public Gallery saveGallery(Gallery gallery) {
        return repository.save(gallery);
    }

    @Override
    public Gallery updateGallery(Long id, Gallery gallery) {

        Gallery existing = getGalleryById(id);

        existing.setTitle(gallery.getTitle());
        existing.setDescription(gallery.getDescription());
        existing.setImageUrl(gallery.getImageUrl());
        existing.setCategory(gallery.getCategory());
        existing.setDisplayOrder(gallery.getDisplayOrder());
        existing.setActive(gallery.getActive());

        return repository.save(existing);
    }

    @Override
    public void deleteGallery(Long id) {
        repository.delete(getGalleryById(id));
    }

    @Override
    public Gallery getGalleryById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gallery Not Found"));
    }

    @Override
    public List<Gallery> getAllGallery() {
        return repository.findAll();
    }

    @Override
    public List<Gallery> getActiveGallery() {
        return repository.findByActiveTrueOrderByDisplayOrderAsc();
    }

    @Override
    public List<Gallery> getGalleryByCategory(String category) {
        return repository.findByCategoryIgnoreCase(category);
    }
}