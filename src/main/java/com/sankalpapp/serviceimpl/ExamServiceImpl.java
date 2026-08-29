package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.request.ExamRequest;
import com.sankalpapp.dto.response.ExamResponse;
import com.sankalpapp.entity.Category;
import com.sankalpapp.entity.Exam;
import com.sankalpapp.repository.CategoryRepository;
import com.sankalpapp.repository.ExamRepository;
import com.sankalpapp.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private static final String folder = "Exam";
    private final ExamRepository examRepository;
    private final CategoryRepository categoryRepository;
    private final S3Service s3Service;

    @Override
    public ExamResponse saveExam(ExamRequest request, MultipartFile image) {
        Exam exam = new Exam();
        updateExamFields(exam, request, image);
        return mapToResponse(examRepository.save(exam));
    }

    @Override
    public ExamResponse updateExam(Long id, ExamRequest request, MultipartFile image) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        updateExamFields(exam, request, image);

        return mapToResponse(examRepository.save(exam));
    }

    @Override
    public void deleteExam(Long id) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        s3Service.deleteFileByUrl(exam.getImage());
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

                // --- New fields incorporated ---
                .testStartDate(exam.getTestStartDate())
                .testEndDate(exam.getTestEndDate())
                .terms(exam.getTerms())
                .image(exam.getImage())

                .downloadTestPaper(exam.getDownloadTestPaper())
                .showTestResult(exam.getShowTestResult())
                .showAllResult(exam.getShowAllResult())
                .allResultPdf(exam.getAllResultPdf())

                .startTime(exam.getStartTime())
                .endTime(exam.getEndTime())
                // -------------------------------

                // Safe category mapping to avoid NullPointerException
                .categoryId(exam.getCategory() != null ? exam.getCategory().getId() : null)
                .categoryName(exam.getCategory() != null ? exam.getCategory().getCategoryName() : null)

                .build();
    }

    /**
     * Updates an existing Exam entity with non-null values from the request.
     */
    private void updateExamFields(Exam exam, ExamRequest request, MultipartFile image) {

        if (exam == null) {
            throw new RuntimeException("Exam cannot be null");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // --- String Fields (Checks for null, empty, and whitespace-only) ---
        if (StringUtils.hasText(request.getExamName())) {
            exam.setExamName(request.getExamName());
        }

        if (StringUtils.hasText(request.getTerms())) {
            exam.setTerms(request.getTerms());
        }

        if (StringUtils.hasText(request.getImage())) {
            exam.setImage(request.getImage());
        }

        if (StringUtils.hasText(request.getAllResultPdf())) {
            exam.setAllResultPdf(request.getAllResultPdf());
        }

        // --- Object / Entity Fields ---
        exam.setCategory(category);

        // --- Date & Time Fields (Standard null checks) ---
        if (request.getExamDate() != null) {
            exam.setExamDate(request.getExamDate());
        }

        if (request.getTestStartDate() != null) {
            exam.setTestStartDate(request.getTestStartDate());
        }

        if (request.getTestEndDate() != null) {
            exam.setTestEndDate(request.getTestEndDate());
        }

        if (request.getStartTime() != null) {
            exam.setStartTime(request.getStartTime());
        }

        if (request.getEndTime() != null) {
            exam.setEndTime(request.getEndTime());
        }

        // --- Numeric Fields (Standard null checks) ---
        if (request.getTotalMarks() != null) {
            exam.setTotalMarks(request.getTotalMarks());
        }

        if (request.getTotalQuestions() != null) {
            exam.setTotalQuestions(request.getTotalQuestions());
        }

        if (request.getDuration() != null) {
            exam.setDuration(request.getDuration());
        }

        // --- Boolean Fields (Standard null checks) ---
        if (request.getDownloadTestPaper() != null) {
            exam.setDownloadTestPaper(request.getDownloadTestPaper());
        }

        if (request.getShowTestResult() != null) {
            exam.setShowTestResult(request.getShowTestResult());
        }

        if (request.getShowAllResult() != null) {
            exam.setShowAllResult(request.getShowAllResult());
        }

        uploadFile(image, exam);
    }

    private void uploadFile(MultipartFile pdf, Exam exam) {
        if (pdf != null) {
            try {
                String fileURL = s3Service.uploadFile(pdf, folder);
                exam.setImage(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
    }

}