package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.WebCourse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    WebCourse createCourse(WebCourse webCourse, String role, String email, MultipartFile courseImage, String url);
    List<WebCourse> getAllCoursesByBranchCode(String role, String email, String branchCode, String url);
    WebCourse updateCourse(int id, WebCourse webCourse, String role, String email, MultipartFile courseImage, String url);
    void deleteCourse(int id, String role, String email, String url);
    WebCourse getCourseById(int id, String role, String email, String url);
}
