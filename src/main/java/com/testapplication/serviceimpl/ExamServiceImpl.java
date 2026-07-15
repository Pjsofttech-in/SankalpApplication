package com.testapplication.serviceimpl;

import com.testapplication.entity.Exam;
import com.testapplication.repository.ExamRepository;
import com.testapplication.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    @Override
    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public Exam updateExam(Long id, Exam exam) {

        Exam existing = getExamById(id);

        existing.setExamName(exam.getExamName());
        existing.setExamDate(exam.getExamDate());
        existing.setTotalMarks(exam.getTotalMarks());
        existing.setTotalQuestions(exam.getTotalQuestions());
        existing.setDuration(exam.getDuration());
        existing.setCategory(exam.getCategory());

        return examRepository.save(existing);
    }

    @Override
    public void deleteExam(Long id) {
        examRepository.delete(getExamById(id));
    }

    @Override
    public Exam getExamById(Long id) {

        return examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found."));
    }

    @Override
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }
}