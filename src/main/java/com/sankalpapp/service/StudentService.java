package com.sankalpapp.service;

import com.sankalpapp.dto.Request.StudentRequest;
import com.sankalpapp.dto.Response.StudentDTO;
import com.sankalpapp.dto.Response.StudentFilterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    StudentDTO saveStudent(StudentRequest request);

    StudentDTO updateStudent(Long id, StudentRequest request);

    void deleteStudent(Long id);

    StudentDTO getStudentById(Long id);

    List<StudentDTO> getAllStudents();

    Page<StudentDTO> getStudents(
            StudentFilterDTO filter,
            Pageable pageable
    );
}