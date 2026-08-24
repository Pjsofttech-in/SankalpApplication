package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebCourse;
import com.sankalpapp.dynamicProfile.entity.WebTestimonials;
import com.sankalpapp.dynamicProfile.repository.CourseRepository;
import com.sankalpapp.dynamicProfile.service.CourseService;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.serviceimpl.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private S3Service s3Service;

    private static final String folder = "Course";

    @Override
    public WebCourse createCourse(WebCourse webCourse, MultipartFile courseImage, String url) {

        // ✅ If it's not the first course, apply color from the first course
        List<WebCourse> allCours = repository.findAll();
        if (!allCours.isEmpty()) {
            webCourse.setCourseColor(allCours.get(0).getCourseColor());
        }
        // ✅ If it's the first course, use the color from the request (leave as-is)

        webCourse.setUrl(url);

        uploadFile(courseImage, webCourse);

        return repository.save(webCourse);
    }

    private void uploadFile(MultipartFile pdf, WebCourse obj) {
        if (pdf != null) {
            try {
                String fileURL = s3Service.uploadFile(pdf, folder);
                obj.setCourseImage(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
    }

    @Override
    public List<WebCourse> getAllCoursesByBranchCode(String url) {
        return repository.findAllOrderById();
    }


    @Override
    public WebCourse updateCourse(int id, WebCourse webCourse, MultipartFile courseImage, String url) {
        WebCourse existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        existing.setCourseName(webCourse.getCourseName() != null ? webCourse.getCourseName() : existing.getCourseName());
        existing.setCourseDescription(webCourse.getCourseDescription() != null ? webCourse.getCourseDescription() : existing.getCourseDescription());
        existing.setUrl(webCourse.getUrl() != null ? webCourse.getUrl() : existing.getUrl());
        existing.setDuration(webCourse.getDuration() != null ? webCourse.getDuration() : existing.getDuration());
        existing.setPrice(webCourse.getPrice() != null ? webCourse.getPrice() : existing.getPrice());

        // If course color is updated, propagate to all courses
        String newColor = webCourse.getCourseColor();
        if (newColor != null && !newColor.equals(existing.getCourseColor())) {
            List<WebCourse> allCours = repository.findAll();
            for (WebCourse c : allCours) {
                c.setCourseColor(newColor);
            }
            repository.saveAll(allCours);
        }

        uploadFile(courseImage, existing);

        return repository.save(existing);
    }


    @Override
    public void deleteCourse(int id, String url) {
        WebCourse webCourse = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        s3Service.deleteFileByUrl(webCourse.getCourseImage());

        repository.deleteById(id);
    }

    @Override
    public WebCourse getCourseById(int id, String url) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }
}