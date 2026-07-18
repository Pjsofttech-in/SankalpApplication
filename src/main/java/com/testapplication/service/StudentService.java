package com.testapplication.service;

import com.testapplication.dto.Request.StudentRequest;
import com.testapplication.dto.Response.StudentResponse;

import java.util.List;

public interface StudentService {

    StudentResponse saveStudent(StudentRequest request);

    StudentResponse updateStudent(Long id, StudentRequest request);

    void deleteStudent(Long id);

    StudentResponse getStudentById(Long id);

    List<StudentResponse> getAllStudents();
}