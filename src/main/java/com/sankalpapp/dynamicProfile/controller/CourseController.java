package com.sankalpapp.dynamicProfile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalpapp.dynamicProfile.entity.WebCourse;
import com.sankalpapp.dynamicProfile.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class CourseController {

    @Autowired
    private CourseService service;

    @PostMapping(value = "/createCourse", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebCourse> createCourse(
            @RequestPart("course") String courseJson,
            @RequestParam String role,
            @RequestParam String email,
            @RequestParam String url,
            @RequestParam("courseImage") MultipartFile courseImageFile) throws JsonProcessingException {

        WebCourse webCourse = new ObjectMapper().readValue(courseJson, WebCourse.class);

        return ResponseEntity.ok(service.createCourse(webCourse, role, email, courseImageFile, url));
    }

    @GetMapping("/getAllCourses")
    public ResponseEntity<List<WebCourse>> getAllCoursesByBranchCode(@RequestParam String role,
                                                                     @RequestParam(required = false) String email,
                                                                     @RequestParam String branchCode,
                                                                     @RequestParam String url) {
        return ResponseEntity.ok(service.getAllCoursesByBranchCode(role, email, branchCode, url));
    }

    @GetMapping("/getCourseById/{id}")
    public ResponseEntity<WebCourse> getCourseById(@PathVariable int id,
                                                   @RequestParam String role,
                                                   @RequestParam String email,
                                                   @RequestParam String url) {
        return ResponseEntity.ok(service.getCourseById(id, role, email, url));
    }

    @PutMapping(value = "/updateCourse/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebCourse> updateCourse(
            @PathVariable int id,
            @RequestPart("course") String courseJson,
            @RequestPart(value = "courseImage", required = false) MultipartFile courseImage,
            @RequestParam String role,
            @RequestParam String email,
            @RequestParam String url) throws JsonProcessingException {

        WebCourse webCourse = new ObjectMapper().readValue(courseJson, WebCourse.class);
        WebCourse updated = service.updateCourse(id, webCourse, role, email, courseImage, url);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/deleteCourse/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id,
                                               @RequestParam String role,
                                               @RequestParam String email,
                                               @RequestParam String url) {
        service.deleteCourse(id, role, email, url);
        return ResponseEntity.ok("Course deleted successfully");
    }

}