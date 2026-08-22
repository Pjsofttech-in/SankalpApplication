package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.WebCourse;
import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import com.sankalpapp.exception.ResourceNotFoundException;
import com.sankalpapp.dynamicProfile.repository.CourseRepository;
import com.sankalpapp.dynamicProfile.repository.SecurityUrlrepository;
import com.sankalpapp.dynamicProfile.service.CourseService;
import com.sankalpapp.dynamicProfile.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private SecurityUrlrepository securityUrlRepository;

    @Autowired
    private S3Service s3Service;

    private void validateUrlExists(String url) {

        String normalizedUrl = normalizeUrl(url);

        securityUrlRepository.findByUrl(normalizedUrl)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "URL [" + url + "] is not allowed"
                ));
    }


    private String normalizeUrl(String url) {
        return (url == null) ? "" : url.split(",")[0].trim().toLowerCase();
    }

    @Override
    public WebCourse createCourse(WebCourse webCourse, MultipartFile courseImage, String url) {
         WebSecurityUrl webSecurityUrl = securityUrlRepository.findByUrl(normalizeUrl(url))
                .orElseThrow(() -> new ResourceNotFoundException("Provided URL does not exist in security URL table"));

        // ✅ If it's not the first course, apply color from the first course
        List<WebCourse> allCours = repository.findAll();
        if (!allCours.isEmpty()) {
            webCourse.setCourseColor(allCours.get(0).getCourseColor());
        }
        // ✅ If it's the first course, use the color from the request (leave as-is)

        webCourse.setUrl(url);
        webCourse.setWebSecurityUrl(webSecurityUrl);

//        if (courseImage != null && !courseImage.isEmpty()) {
//            try {
//                String imageUrl = s3Service.uploadImage(courseImage);
//                webCourse.setCourseImage(imageUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload course image", e);
//            }
//        }

        return repository.save(webCourse);
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

//        if (courseImage != null && !courseImage.isEmpty()) {
//            try {
//                String newImageUrl = s3Service.uploadImage(courseImage);
//
//                if (existing.getCourseImage() != null && existing.getCourseImage().contains("amazonaws.com")) {
//                    s3Service.deleteImage(existing.getCourseImage());
//                }
//
//                existing.setCourseImage(newImageUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload course image", e);
//            }
//        }

        return repository.save(existing);
    }


    @Override
    public void deleteCourse(int id, String url) {
         WebCourse webCourse = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if (webCourse.getCourseImage() != null && webCourse.getCourseImage().contains("amazonaws.com")) {
            s3Service.deleteImage(webCourse.getCourseImage());
        }

        repository.deleteById(id);
    }

    @Override
    public WebCourse getCourseById(int id, String url) {
         return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }
}