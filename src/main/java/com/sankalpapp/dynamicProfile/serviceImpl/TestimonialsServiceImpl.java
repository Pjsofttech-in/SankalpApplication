package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.dynamicProfile.entity.WebTestimonials;
import com.sankalpapp.dynamicProfile.repository.TestimonialsRepository;
import com.sankalpapp.dynamicProfile.service.S3Service;
import com.sankalpapp.dynamicProfile.service.TestimonialsService;
import com.sankalpapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TestimonialsServiceImpl implements TestimonialsService {

    @Autowired
    private TestimonialsRepository repository;

    @Autowired
    private S3Service s3Service;

    private String normalizeUrl(String url) {
        if (url == null) return "";
        return url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebTestimonials create(WebTestimonials webTestimonials, MultipartFile testimonialImage, String url) {
        // Static color logic
        List<WebTestimonials> existingTestimonials = repository.findAll();
        if (!existingTestimonials.isEmpty()) {
            webTestimonials.setTestimonialColor(existingTestimonials.getFirst().getTestimonialColor());
        }

        webTestimonials.setUrl(url);

//        if (testimonialImage != null && !testimonialImage.isEmpty()) {
//            try {
//                String imageUrl = s3Service.uploadImage(testimonialImage);
//                webTestimonials.setTestimonialImage(imageUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload testimonial image", e);
//            }
//        }

        return repository.save(webTestimonials);
    }

    @Override
    public List<WebTestimonials> getAllByBranchCode(String url) {
        return repository.findAllOrderById();
    }


    @Override
    public WebTestimonials update(Long id, WebTestimonials webTestimonials, MultipartFile testimonialImage, String url) {
        WebTestimonials existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimonial not found"));

        if (webTestimonials.getTestimonialTitle() != null)
            existing.setTestimonialTitle(webTestimonials.getTestimonialTitle());
        if (webTestimonials.getTestimonialName() != null)
            existing.setTestimonialName(webTestimonials.getTestimonialName());
        if (webTestimonials.getExam() != null)
            existing.setExam(webTestimonials.getExam());
        if (webTestimonials.getPost() != null)
            existing.setPost(webTestimonials.getPost());
        if (webTestimonials.getRank() != null)
            existing.setRank(webTestimonials.getRank());
        if (webTestimonials.getDescription() != null)
            existing.setDescription(webTestimonials.getDescription());

        // Static color logic
        if (webTestimonials.getTestimonialColor() != null &&
                !webTestimonials.getTestimonialColor().equals(existing.getTestimonialColor())) {
            List<WebTestimonials> allTestimonials = repository.findAll();
            for (WebTestimonials t : allTestimonials) {
                t.setTestimonialColor(webTestimonials.getTestimonialColor());
            }
            repository.saveAll(allTestimonials);
            existing.setTestimonialColor(webTestimonials.getTestimonialColor());
        }

//        if (testimonialImage != null && !testimonialImage.isEmpty()) {
//            try {
//                String imageUrl = s3Service.uploadImage(testimonialImage);
//                if (existing.getTestimonialImage() != null && existing.getTestimonialImage().contains("amazonaws.com")) {
//                    s3Service.deleteImage(existing.getTestimonialImage());
//                }
//                existing.setTestimonialImage(imageUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload testimonial image", e);
//            }
//        }

        return repository.save(existing);
    }


    @Override
    public void delete(Long id, String url) {
        WebTestimonials testimonial = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimonial not found"));

        if (testimonial.getTestimonialImage() != null && testimonial.getTestimonialImage().contains("amazonaws.com")) {
            s3Service.deleteImage(testimonial.getTestimonialImage());
        }

        repository.deleteById(id);
    }

    @Override
    public WebTestimonials getById(Long id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimonial not found"));
    }
}
