package com.sankalpapp.service;

import com.sankalpapp.entity.Syllabus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SyllabusService {

    Syllabus createSyllabus(Syllabus syllabus, MultipartFile file);

    List<Syllabus> getAllSyllabus();

    Syllabus getSyllabusById(Long id);

    Syllabus updateSyllabus(Long id, Syllabus syllabus, MultipartFile file);

    void deleteSyllabus(Long id);
}