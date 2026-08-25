package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Request.TestSeriesExamRequest;
import com.sankalpapp.dto.Request.TestSeriesRequest;
import com.sankalpapp.dto.Response.TestSeriesExamResponse;
import com.sankalpapp.dto.Response.TestSeriesProgressExamResponse;
import com.sankalpapp.dto.Response.TestSeriesProgressResponse;
import com.sankalpapp.dto.Response.TestSeriesResponse;
import com.sankalpapp.entity.*;
import com.sankalpapp.repository.*;
import com.sankalpapp.service.TestSeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestSeriesServiceImpl implements TestSeriesService {

    private final TestSeriesRepository testSeriesRepository;
    private final TestSeriesExamRepository testSeriesExamRepository;
    private final ExamRepository examRepository;

    private final ExamAttemptRepository examAttemptRepository;
    private final ResultRepository resultRepository;

    @Override
    public TestSeriesResponse create(
            TestSeriesRequest request
    ) {

        TestSeries testSeries = TestSeries.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .active(
                        request.getActive() != null
                                ? request.getActive()
                                : true
                )
                .build();

        testSeries = testSeriesRepository.save(testSeries);

        return mapToResponse(testSeries);
    }

    @Override
    public TestSeriesResponse update(
            Long id,
            TestSeriesRequest request
    ) {

        TestSeries testSeries =
                testSeriesRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Test Series not found with id: " + id
                                )
                        );

        if (request.getTitle() != null) {
            testSeries.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            testSeries.setDescription(
                    request.getDescription()
            );
        }

        if (request.getActive() != null) {
            testSeries.setActive(
                    request.getActive()
            );
        }

        testSeriesRepository.save(testSeries);

        return mapToResponse(testSeries);
    }

    @Override
    @Transactional(readOnly = true)
    public TestSeriesResponse getById(
            Long id
    ) {

        TestSeries testSeries =
                testSeriesRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Test Series not found with id: " + id
                                )
                        );

        return mapToResponse(testSeries);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestSeriesResponse> getAll() {

        return testSeriesRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void delete(
            Long id
    ) {

        TestSeries testSeries =
                testSeriesRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Test Series not found with id: " + id
                                )
                        );

        testSeriesRepository.delete(testSeries);
    }

    @Override
    public TestSeriesResponse addExam(
            Long testSeriesId,
            TestSeriesExamRequest request
    ) {

        TestSeries testSeries =
                testSeriesRepository.findById(testSeriesId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Test Series not found with id: "
                                                + testSeriesId
                                )
                        );

        Exam exam =
                examRepository.findById(request.getExamId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Exam not found with id: "
                                                + request.getExamId()
                                )
                        );

        if (testSeriesExamRepository
                .existsByTestSeriesIdAndExamId(
                        testSeriesId,
                        request.getExamId()
                )) {

            throw new RuntimeException(
                    "Exam is already present in this test series"
            );
        }

        TestSeriesExam testSeriesExam =
                TestSeriesExam.builder()
                        .testSeries(testSeries)
                        .exam(exam)
                        .sequence(request.getSequence())
                        .active(true)
                        .build();

        testSeriesExamRepository.save(testSeriesExam);

        return mapToResponse(testSeries);
    }

    @Override
    @Transactional(readOnly = true)
    public TestSeriesProgressResponse getStudentProgress(
            Long testSeriesId,
            Long studentId
    ) {

        TestSeries testSeries =
                testSeriesRepository.findById(testSeriesId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Test Series not found with id: "
                                                + testSeriesId
                                )
                        );

        List<TestSeriesExam> seriesExams =
                testSeriesExamRepository
                        .findByTestSeriesIdOrderBySequenceAsc(
                                testSeriesId
                        );

        List<TestSeriesProgressExamResponse> progress =
                seriesExams.stream()
                        .map(seriesExam ->
                                buildProgressForExam(
                                        seriesExam,
                                        studentId
                                )
                        )
                        .toList();

        int completedExams = (int) progress.stream()
                .filter(p ->
                        "PUBLISHED".equals(p.getStatus())
                )
                .count();

        double overallPercentage =
                progress.stream()
                        .filter(p -> p.getPercentage() != null)
                        .mapToDouble(
                                TestSeriesProgressExamResponse::getPercentage
                        )
                        .average()
                        .orElse(0.0);

        return TestSeriesProgressResponse.builder()
                .testSeriesId(testSeries.getId())
                .title(testSeries.getTitle())
                .description(testSeries.getDescription())
                .totalExams(seriesExams.size())
                .completedExams(completedExams)
                .overallPercentage(
                        Math.round(overallPercentage * 100.0) / 100.0
                )
                .exams(progress)
                .build();
    }

    private TestSeriesProgressExamResponse buildProgressForExam(
            TestSeriesExam seriesExam,
            Long studentId
    ) {

        Exam exam = seriesExam.getExam();

        TestSeriesProgressExamResponse.TestSeriesProgressExamResponseBuilder response =
                TestSeriesProgressExamResponse.builder()
                        .examId(exam.getId())
                        .examName(exam.getExamName())
                        .sequence(seriesExam.getSequence())
                        .totalMarks(exam.getTotalMarks())
                        .totalQuestions(exam.getTotalQuestions())
                        .duration(exam.getDuration())
                        .obtainedMarks(null)
                        .percentage(null)
                        .grade(null)
                        .published(false);

        Optional<ExamAttempt> attempt =
                examAttemptRepository
                        .findTopByStudentIdAndExamIdOrderByIdDesc(
                                studentId,
                                exam.getId()
                        );

        if (attempt.isEmpty()) {

            return response
                    .status("NOT_STARTED")
                    .build();
        }

        ExamAttempt examAttempt = attempt.get();

        if (examAttempt.getStatus()
                == ExamAttempt.AttemptStatus.STARTED) {

            return response
                    .status("IN_PROGRESS")
                    .build();
        }

        if (examAttempt.getStatus()
                == ExamAttempt.AttemptStatus.SUBMITTED) {

            return response
                    .status("SUBMITTED")
                    .build();
        }

        Optional<Result> result =
                resultRepository.findByAttemptId(
                        examAttempt.getId()
                );

        if (result.isEmpty()) {

            return response
                    .status("EVALUATED")
                    .build();
        }

        Result r = result.get();

        String status =
                Boolean.TRUE.equals(r.getPublished())
                        ? "PUBLISHED"
                        : "EVALUATED";

        return response
                .status(status)
                .obtainedMarks(r.getObtainedMarks())
                .percentage(r.getPercentage())
                .grade(r.getGrade())
                .published(r.getPublished())
                .build();
    }

    @Override
    public TestSeriesResponse removeExam(
            Long testSeriesId,
            Long examId
    ) {

        if (!testSeriesRepository.existsById(testSeriesId)) {
            throw new RuntimeException(
                    "Test Series not found with id: "
                            + testSeriesId
            );
        }

        TestSeriesExam testSeriesExam =
                testSeriesExamRepository
                        .findByTestSeriesIdAndExamId(
                                testSeriesId,
                                examId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Exam is not present in this test series"
                                )
                        );

        testSeriesExamRepository.delete(testSeriesExam);

        return getById(testSeriesId);
    }

    private TestSeriesResponse mapToResponse(
            TestSeries testSeries
    ) {

        List<TestSeriesExam> seriesExams =
                testSeriesExamRepository
                        .findByTestSeriesIdOrderBySequenceAsc(
                                testSeries.getId()
                        );

        List<TestSeriesExamResponse> examResponses =
                seriesExams.stream()
                        .map(this::mapExam)
                        .toList();

        return TestSeriesResponse.builder()
                .id(testSeries.getId())
                .title(testSeries.getTitle())
                .description(testSeries.getDescription())
                .active(testSeries.getActive())
                .exams(examResponses)
                .createdAt(testSeries.getCreatedAt())
                .updatedAt(testSeries.getUpdatedAt())
                .build();
    }

    private TestSeriesExamResponse mapExam(
            TestSeriesExam testSeriesExam
    ) {

        Exam exam = testSeriesExam.getExam();

        return TestSeriesExamResponse.builder()
                .id(testSeriesExam.getId())
                .examId(exam.getId())
                .examName(exam.getExamName())
                .sequence(testSeriesExam.getSequence())
                .totalQuestions(exam.getTotalQuestions())
                .totalMarks(exam.getTotalMarks())
                .duration(exam.getDuration())
                .active(testSeriesExam.getActive())
                .build();
    }
}