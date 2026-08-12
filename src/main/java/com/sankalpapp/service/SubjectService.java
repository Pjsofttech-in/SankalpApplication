package com.sankalpapp.service;

import com.sankalpapp.entity.Subject;

import java.util.List;

public interface SubjectService {

    Subject createSubject(Subject subject);

    Subject updateSubject(Long id, Subject subject);

    List<Subject> getAllSubjects();

    Subject getSubjectById(Long id);

    void deleteSubject(Long id);
}
