package com.sankalpapp.serviceimpl;

import com.sankalpapp.entity.Syllabus;
import com.sankalpapp.repository.SyllabusRepository;
import com.sankalpapp.service.SyllabusService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyllabusServiceImpl implements SyllabusService {

    private final SyllabusRepository syllabusRepository;

    public SyllabusServiceImpl(SyllabusRepository syllabusRepository) {
        this.syllabusRepository = syllabusRepository;
    }

    @Override
    public Syllabus createSyllabus(Syllabus syllabus) {
        return syllabusRepository.save(syllabus);
    }

    @Override
    public List<Syllabus> getAllSyllabus() {
        return syllabusRepository.findAll();
    }

    @Override
    public Syllabus getSyllabusById(Long id) {
        return syllabusRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Syllabus not found with id: " + id)
                );
    }

    @Override
    public Syllabus updateSyllabus(Long id, Syllabus syllabus) {

        Syllabus existingSyllabus = syllabusRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Syllabus not found with id: " + id)
                );

        existingSyllabus.setTitle(syllabus.getTitle());
        existingSyllabus.setLink(syllabus.getLink());

        return syllabusRepository.save(existingSyllabus);
    }

    @Override
    public void deleteSyllabus(Long id) {

        Syllabus existingSyllabus = syllabusRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Syllabus not found with id: " + id)
                );

        syllabusRepository.delete(existingSyllabus);
    }
}