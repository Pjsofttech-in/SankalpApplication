package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebCourse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    WebCourse createCourse(WebCourse webCourse, MultipartFile courseImage, String url);

    List<WebCourse> getAllCoursesByBranchCode(String url);

    WebCourse updateCourse(int id, WebCourse webCourse, MultipartFile courseImage, String url);

    void deleteCourse(int id, String url);

    WebCourse getCourseById(int id, String url);
}
