package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebTestimonials;
import com.sankalpapp.dynamicProfile.repository.TestimonialsRepository;
import com.sankalpapp.dynamicProfile.service.TestimonialsService;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.serviceimpl.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TestimonialsServiceImpl implements TestimonialsService {

    @Autowired
    private TestimonialsRepository repository;

    @Autowired
    private S3Service s3Service;

    private static final String folder = "Testimonials";

    @Override
    public WebTestimonials create(WebTestimonials webTestimonials, MultipartFile testimonialImage, String url) {
        // Static color logic
        List<WebTestimonials> existingTestimonials = repository.findAll();
        if (!existingTestimonials.isEmpty()) {
            webTestimonials.setTestimonialColor(existingTestimonials.getFirst().getTestimonialColor());
        }

        webTestimonials.setUrl(url);

        uploadFile(testimonialImage, webTestimonials);

        return repository.save(webTestimonials);
    }

    private void uploadFile(MultipartFile pdf, WebTestimonials obj) {
        if (pdf != null) {
            try {
                String fileURL = s3Service.uploadFile(pdf, folder);
                obj.setTestimonialImage(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
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

        uploadFile(testimonialImage, existing);

        return repository.save(existing);
    }


    @Override
    public void delete(Long id, String url) {
        WebTestimonials testimonial = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimonial not found"));

        s3Service.deleteFileByUrl(testimonial.getTestimonialImage());

        repository.deleteById(id);
    }

    @Override
    public WebTestimonials getById(Long id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimonial not found"));
    }
}
