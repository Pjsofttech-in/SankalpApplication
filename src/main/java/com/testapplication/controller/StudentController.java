package com.testapplication.controller;

import com.testapplication.dto.Request.StudentRequest;
import com.testapplication.dto.Response.StudentResponse;
import com.testapplication.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    // Save Student
    @PostMapping
    public StudentResponse saveStudent(@RequestBody StudentRequest request) {

        return studentService.saveStudent(request);
    }

    // Get All Students
    @GetMapping
    public List<StudentResponse> getAllStudents() {

        return studentService.getAllStudents();
    }

    // Get Student By Id
    @GetMapping("/{id}")
    public StudentResponse getStudentById(@PathVariable Long id) {

        return studentService.getStudentById(id);
    }

    // Update Student
    @PutMapping("/{id}")
    public StudentResponse updateStudent(@PathVariable Long id,
                                         @RequestBody StudentRequest request) {

        return studentService.updateStudent(id, request);
    }

    // Delete Student
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);

        return "Student Deleted Successfully";
    }
}