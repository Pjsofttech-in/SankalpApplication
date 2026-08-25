package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Request.AddExamToTestSeriesRequest;
import com.sankalpapp.dto.Request.CreateTestSeriesRequest;
import com.sankalpapp.dto.Request.ReorderExamRequest;
import com.sankalpapp.entity.Exam;
import com.sankalpapp.entity.TestSeries;
import com.sankalpapp.entity.TestSeriesExam;
import com.sankalpapp.repository.ExamRepository;
import com.sankalpapp.repository.TestSeriesExamRepository;
import com.sankalpapp.repository.TestSeriesRepository;
import com.sankalpapp.service.TestSeriesService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestSeriesServiceImpl implements TestSeriesService {

    private final TestSeriesRepository testSeriesRepository;
    private final TestSeriesExamRepository testSeriesExamRepository;
    private final ExamRepository examRepository;

    public TestSeriesServiceImpl(
            TestSeriesRepository testSeriesRepository,
            TestSeriesExamRepository testSeriesExamRepository,
            ExamRepository examRepository
    ) {
        this.testSeriesRepository = testSeriesRepository;
        this.testSeriesExamRepository = testSeriesExamRepository;
        this.examRepository = examRepository;
    }

    @Override
    @Transactional
    public TestSeries create(
            CreateTestSeriesRequest request
    ) {

        TestSeries testSeries = TestSeries.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .image(request.getImage())
                .price(request.getPrice())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .active(
                        request.getActive() != null
                                ? request.getActive()
                                : true
                )
                .build();

        return testSeriesRepository.save(testSeries);
    }

    @Override
    public TestSeries getById(Long id) {

        return testSeriesRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Test series not found with id: " + id
                        )
                );
    }

    @Override
    public List<TestSeries> getAll() {

        return testSeriesRepository.findAll();
    }

    @Override
    @Transactional
    public TestSeries update(
            Long id,
            CreateTestSeriesRequest request
    ) {

        TestSeries testSeries = getById(id);

        testSeries.setTitle(request.getTitle());
        testSeries.setDescription(request.getDescription());
        testSeries.setImage(request.getImage());
        testSeries.setPrice(request.getPrice());
        testSeries.setStartDate(request.getStartDate());
        testSeries.setEndDate(request.getEndDate());

        if (request.getActive() != null) {
            testSeries.setActive(request.getActive());
        }

        return testSeriesRepository.save(testSeries);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        TestSeries testSeries = getById(id);

        testSeriesRepository.delete(testSeries);
    }

    @Override
    @Transactional
    public void addExam(
            Long testSeriesId,
            AddExamToTestSeriesRequest request
    ) {

        TestSeries testSeries = getById(testSeriesId);

        Exam exam = examRepository.findById(
                        request.getExamId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exam not found with id: "
                                        + request.getExamId()
                        )
                );

        /*
         * Prevent the same exam from being added twice.
         */
        if (testSeriesExamRepository
                .findByTestSeriesAndExam(testSeries, exam)
                .isPresent()) {

            throw new RuntimeException(
                    "Exam is already part of this test series"
            );
        }

        /*
         * If sequence is not provided,
         * put the exam at the end.
         */
        Integer sequence = request.getSequence();

        if (sequence == null) {

            List<TestSeriesExam> existing =
                    testSeriesExamRepository
                            .findByTestSeriesOrderBySequenceAsc(
                                    testSeries
                            );

            sequence = existing.size() + 1;
        }

        TestSeriesExam testSeriesExam =
                TestSeriesExam.builder()
                        .testSeries(testSeries)
                        .exam(exam)
                        .sequence(sequence)
                        .active(true)
                        .build();

        testSeriesExamRepository.save(testSeriesExam);
    }

    @Override
    @Transactional
    public void removeExam(
            Long testSeriesId,
            Long examId
    ) {

        TestSeries testSeries = getById(testSeriesId);

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exam not found with id: " + examId
                        )
                );

        TestSeriesExam testSeriesExam =
                testSeriesExamRepository
                        .findByTestSeriesAndExam(
                                testSeries,
                                exam
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Exam is not part of this test series"
                                )
                        );

        testSeriesExamRepository.delete(testSeriesExam);
    }

    @Override
    @Transactional
    public void reorderExam(
            Long testSeriesId,
            Long examId,
            ReorderExamRequest request
    ) {

        TestSeries testSeries = getById(testSeriesId);

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exam not found with id: " + examId
                        )
                );

        TestSeriesExam testSeriesExam =
                testSeriesExamRepository
                        .findByTestSeriesAndExam(
                                testSeries,
                                exam
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Exam is not part of this test series"
                                )
                        );

        testSeriesExam.setSequence(
                request.getSequence()
        );

        testSeriesExamRepository.save(testSeriesExam);
    }
}