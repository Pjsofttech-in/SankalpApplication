package com.sankalpapp.service;

import com.sankalpapp.entity.Syllabus;

import java.util.List;

public interface SyllabusService {

    Syllabus createSyllabus(Syllabus syllabus);

    List<Syllabus> getAllSyllabus();

    Syllabus getSyllabusById(Long id);

    Syllabus updateSyllabus(Long id, Syllabus syllabus);

    void deleteSyllabus(Long id);
}