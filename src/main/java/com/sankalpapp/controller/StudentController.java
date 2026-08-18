package com.sankalpapp.controller;

import com.sankalpapp.dto.Request.StudentRequest;
import com.sankalpapp.dto.Response.StudentDTO;
import com.sankalpapp.dto.Response.StudentFilterDTO;
import com.sankalpapp.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public StudentDTO saveStudent(@RequestBody StudentRequest request) {

        return studentService.saveStudent(request);
    }

    @PostMapping("/filter")
    public Page<StudentDTO> getStudents(
            @RequestBody StudentFilterDTO filter,

            @PageableDefault(
                    page = 0,
                    size = 20,
                    sort = "studentName",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable
    ) {

        return studentService.getStudents(
                filter,
                pageable
        );
    }

    // Get All Students
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public List<StudentDTO> getAllStudents() {

        return studentService.getAllStudents();
    }

    // Get Student By Id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR','STUDENT')")
    public StudentDTO getStudentById(@PathVariable Long id) {

        return studentService.getStudentById(id);
    }

    // Update Student
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','COORDINATOR')")
    public StudentDTO updateStudent(@PathVariable Long id,
                                    @RequestBody StudentRequest request) {

        return studentService.updateStudent(id, request);
    }

    // Delete Student
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);

        return "Student Deleted Successfully";
    }
}