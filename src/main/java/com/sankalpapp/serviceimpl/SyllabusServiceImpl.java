package com.sankalpapp.serviceimpl;

import com.sankalpapp.entity.Syllabus;
import com.sankalpapp.repository.SyllabusRepository;
import com.sankalpapp.service.SyllabusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SyllabusServiceImpl implements SyllabusService {

    private static final String folder = "Syllabus";
    private final SyllabusRepository syllabusRepository;
    private final S3Service s3service;

    @Override
    public Syllabus createSyllabus(Syllabus syllabus, MultipartFile file) {
        uploadFile(file, syllabus);
        return syllabusRepository.save(syllabus);
    }

    private void uploadFile(MultipartFile pdf, Syllabus syllabus) {
        if (pdf != null) {
            try {
                String fileURL = s3service.uploadFile(pdf, folder);
                syllabus.setLink(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
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
    public Syllabus updateSyllabus(Long id, Syllabus syllabus, MultipartFile file) {

        Syllabus existingSyllabus = syllabusRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Syllabus not found with id: " + id)
                );

        existingSyllabus.setTitle(syllabus.getTitle());
        existingSyllabus.setLink(syllabus.getLink());
        uploadFile(file, syllabus);

        return syllabusRepository.save(existingSyllabus);
    }

    @Override
    public void deleteSyllabus(Long id) {

        Syllabus existingSyllabus = syllabusRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Syllabus not found with id: " + id)
                );

        s3service.deleteFileByUrl(existingSyllabus.getLink());

        syllabusRepository.delete(existingSyllabus);
    }
}