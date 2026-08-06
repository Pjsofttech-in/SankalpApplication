package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Request.ExamRequest;
import com.sankalpapp.dto.Response.ExamResponse;
import com.sankalpapp.entity.Category;
import com.sankalpapp.entity.Exam;
import com.sankalpapp.repository.CategoryRepository;
import com.sankalpapp.repository.ExamRepository;
import com.sankalpapp.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ExamResponse saveExam(ExamRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Exam exam = Exam.builder()
                .examName(request.getExamName())
                .examDate(request.getExamDate())
                .totalMarks(request.getTotalMarks())
                .totalQuestions(request.getTotalQuestions())
                .duration(request.getDuration())
                .category(category)
                .build();

        return mapToResponse(examRepository.save(exam));
    }

    @Override
    public ExamResponse updateExam(Long id, ExamRequest request) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        exam.setExamName(request.getExamName());
        exam.setExamDate(request.getExamDate());
        exam.setTotalMarks(request.getTotalMarks());
        exam.setTotalQuestions(request.getTotalQuestions());
        exam.setDuration(request.getDuration());
        exam.setCategory(category);

        return mapToResponse(examRepository.save(exam));
    }

    @Override
    public void deleteExam(Long id) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        examRepository.delete(exam);
    }

    @Override
    public ExamResponse getExamById(Long id) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        return mapToResponse(exam);
    }

    @Override
    public List<ExamResponse> getAllExams() {

        return examRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ExamResponse mapToResponse(Exam exam) {

        return ExamResponse.builder()
                .id(exam.getId())
                .examName(exam.getExamName())
                .examDate(exam.getExamDate())
                .totalMarks(exam.getTotalMarks())
                .totalQuestions(exam.getTotalQuestions())
                .duration(exam.getDuration())

                .categoryId(exam.getCategory().getId())
                .categoryName(exam.getCategory().getCategoryName())

                .build();
    }
}