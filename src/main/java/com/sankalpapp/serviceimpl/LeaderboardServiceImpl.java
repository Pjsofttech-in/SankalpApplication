package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.response.LeaderboardResponse;
import com.sankalpapp.entity.*;
import com.sankalpapp.repository.ExamRepository;
import com.sankalpapp.repository.ResultRepository;
import com.sankalpapp.repository.TestSeriesExamRepository;
import com.sankalpapp.repository.TestSeriesRepository;
import com.sankalpapp.service.LeaderboardPdfService;
import com.sankalpapp.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LeaderboardServiceImpl
        implements LeaderboardService {

    private static final String folder = "Exams/Leaderboards";
    private final ResultRepository resultRepository;
    private final ExamRepository examRepository;
    private final TestSeriesRepository testSeriesRepository;
    private final TestSeriesExamRepository testSeriesExamRepository;
    private final LeaderboardPdfService leaderboardPdfService;
    private final S3Service s3Service;

    @Override
    @Transactional(readOnly = true)
    public List<LeaderboardResponse> getExamLeaderboard(
            Long examId
    ) {

        List<Result> results =
                resultRepository
                        .findByExamIdAndPublishedTrueAndActiveTrue(
                                examId
                        );

        /*
         * Keep only the best result for each student.
         *
         * Best result means:
         * 1. Higher obtained marks
         * 2. If marks are same, lower time taken
         */
        Map<Long, Result> bestResults = new HashMap<>();

        for (Result result : results) {

            Long studentId =
                    result.getStudent().getId();

            Result existing =
                    bestResults.get(studentId);

            if (existing == null) {

                bestResults.put(studentId, result);

            } else {

                long existingTime =
                        getTimeTaken(existing);

                long currentTime =
                        getTimeTaken(result);

                boolean betterResult =
                        result.getObtainedMarks()
                                > existing.getObtainedMarks()

                                ||

                                (
                                        result.getObtainedMarks()
                                                .equals(
                                                        existing.getObtainedMarks()
                                                )

                                                && currentTime < existingTime
                                );

                if (betterResult) {
                    bestResults.put(studentId, result);
                }
            }
        }

        /*
         * Convert best results into leaderboard responses.
         */
        List<LeaderboardResponse> leaderboard =
                new ArrayList<>();

        for (Result result : bestResults.values()) {

            long timeTakenSeconds =
                    getTimeTaken(result);

            leaderboard.add(
                    LeaderboardResponse.builder()

                            .studentId(
                                    result.getStudent().getId()
                            )

                            .studentName(
                                    result.getStudent()
                                            .getStudentName()
                            )

                            .obtainedMarks(
                                    result.getObtainedMarks()
                            )

                            .totalMarks(
                                    result.getTotalMarks()
                            )

                            .percentage(
                                    result.getPercentage()
                            )

                            .timeTakenSeconds(
                                    timeTakenSeconds
                            )

                            .build()
            );
        }

        /*
         * Sort:
         *
         * 1. Higher marks first
         * 2. Lower time first
         */
        leaderboard.sort(
                Comparator
                        .comparing(
                                LeaderboardResponse::getObtainedMarks,
                                Comparator.reverseOrder()
                        )
                        .thenComparing(
                                LeaderboardResponse::getTimeTakenSeconds
                        )
        );

        /*
         * Assign competition ranking.
         *
         * Example:
         *
         * 95 marks / 30 sec → Rank 1
         * 95 marks / 30 sec → Rank 1
         * 95 marks / 40 sec → Rank 3
         * 90 marks / 30 sec → Rank 4
         */
        int rank = 1;

        for (int i = 0; i < leaderboard.size(); i++) {

            LeaderboardResponse current =
                    leaderboard.get(i);

            if (i > 0) {

                LeaderboardResponse previous =
                        leaderboard.get(i - 1);

                boolean sameMarks =
                        current.getObtainedMarks()
                                .equals(
                                        previous.getObtainedMarks()
                                );

                boolean sameTime =
                        current.getTimeTakenSeconds()
                                .equals(
                                        previous.getTimeTakenSeconds()
                                );

                if (!sameMarks || !sameTime) {

                    rank = i + 1;
                }
            }

            current.setRank(rank);
        }

        return leaderboard;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaderboardResponse> getTestSeriesLeaderboard(
            Long testSeriesId
    ) {

        /*
         * We'll implement this next.
         */
        throw new UnsupportedOperationException(
                "Test series leaderboard not implemented yet"
        );
    }

    private long getTimeTaken(Result result) {

        if (result.getAttempt() == null) {
            return Long.MAX_VALUE;
        }

        if (result.getAttempt().getStartedAt() == null) {
            return Long.MAX_VALUE;
        }

        if (result.getAttempt().getSubmittedAt() == null) {
            return Long.MAX_VALUE;
        }

        return Duration.between(
                result.getAttempt().getStartedAt(),
                result.getAttempt().getSubmittedAt()
        ).getSeconds();
    }

    @Override
    @Transactional
    public void finalizeExamLeaderboard(Long examId) {

        Exam exam =
                examRepository.findById(examId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Exam not found"
                                )
                        );

        /*
         * Don't finalize twice.
         */
        if (Boolean.TRUE.equals(
                exam.getResultFinalized()
        )) {

            throw new RuntimeException(
                    "Exam leaderboard is already finalized"
            );
        }

        /*
         * IMPORTANT:
         *
         * Your current Exam entity has only examDate,
         * not an exam end time.
         *
         * So we cannot safely check whether the exam
         * has ended yet.
         */

        List<Result> results =
                resultRepository
                        .findByExamIdAndPublishedTrueAndActiveTrue(
                                examId
                        );

        if (results.isEmpty()) {

            throw new RuntimeException(
                    "No published results found for this exam"
            );
        }

        /*
         * At this stage we only verify that ranking
         * can be generated successfully.
         *
         * PDF generation + R2 upload comes next.
         */
        List<LeaderboardResponse> leaderboard =
                buildFinalExamLeaderboard(results);

        if (leaderboard.isEmpty()) {

            throw new RuntimeException(
                    "Unable to generate leaderboard"
            );
        }

        byte[] pdf =
                leaderboardPdfService
                        .generateExamLeaderboardPdf(
                                examId,
                                leaderboard
                        );

        uploadFile(pdf, exam);

        exam.setResultFinalized(true);

        examRepository.save(exam);
    }

    private void uploadFile(byte[] pdf, Exam exam) {
        if (pdf != null) {
            try {
                String pdfUrl =
                        s3Service.uploadFile(
                                pdf,
                                exam.getExamName() + "-" + exam.getId() + "-leaderboard.pdf",
                                "application/pdf",
                                "leaderboards/exams"
                        );
                exam.setAllResultPdf(pdfUrl);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
    }

    private List<LeaderboardResponse> buildFinalExamLeaderboard(
            List<Result> results
    ) {

        /*
         * Keep only the best result for each student.
         *
         * Best result:
         * 1. Higher marks
         * 2. If marks are equal → less time
         */
        Map<Long, Result> bestResults = new HashMap<>();

        for (Result result : results) {

            Long studentId =
                    result.getStudent().getId();

            Result existing =
                    bestResults.get(studentId);

            if (existing == null) {

                bestResults.put(studentId, result);

                continue;
            }

            long existingTime =
                    getTimeTaken(existing);

            long currentTime =
                    getTimeTaken(result);

            boolean better =
                    result.getObtainedMarks()
                            > existing.getObtainedMarks()

                            ||

                            (
                                    result.getObtainedMarks()
                                            .equals(
                                                    existing.getObtainedMarks()
                                            )
                                            &&
                                            currentTime < existingTime
                            );

            if (better) {
                bestResults.put(studentId, result);
            }
        }

        /*
         * Convert Results → LeaderboardResponse
         */
        List<LeaderboardResponse> leaderboard =
                new ArrayList<>();

        for (Result result : bestResults.values()) {

            long timeTaken =
                    getTimeTaken(result);

            leaderboard.add(
                    LeaderboardResponse.builder()
                            .studentId(
                                    result.getStudent().getId()
                            )
                            .studentName(
                                    result.getStudent()
                                            .getStudentName()
                            )
                            .obtainedMarks(
                                    result.getObtainedMarks()
                            )
                            .totalMarks(
                                    result.getTotalMarks()
                            )
                            .percentage(
                                    result.getPercentage()
                            )
                            .timeTakenSeconds(
                                    timeTaken
                            )
                            .build()
            );
        }

        /*
         * Sort:
         *
         * Marks → DESC
         * Time  → ASC
         */
        leaderboard.sort(
                Comparator
                        .comparing(
                                LeaderboardResponse::getObtainedMarks,
                                Comparator.reverseOrder()
                        )
                        .thenComparing(
                                LeaderboardResponse::getTimeTakenSeconds
                        )
        );

        /*
         * Competition ranking.
         *
         * Example:
         *
         * 20 marks / 100 sec → 1
         * 20 marks / 100 sec → 1
         * 20 marks / 120 sec → 3
         * 18 marks / 90 sec  → 4
         */
        int rank = 1;

        for (int i = 0;
             i < leaderboard.size();
             i++) {

            LeaderboardResponse current =
                    leaderboard.get(i);

            if (i > 0) {

                LeaderboardResponse previous =
                        leaderboard.get(i - 1);

                boolean sameMarks =
                        current.getObtainedMarks()
                                .equals(
                                        previous.getObtainedMarks()
                                );

                boolean sameTime =
                        current.getTimeTakenSeconds()
                                .equals(
                                        previous.getTimeTakenSeconds()
                                );

                if (!sameMarks || !sameTime) {
                    rank = i + 1;
                }
            }

            current.setRank(rank);
        }

        return leaderboard;
    }

    @Override
    @Transactional
    public void finalizeTestSeriesLeaderboard(
            Long testSeriesId
    ) {

        TestSeries testSeries =
                testSeriesRepository
                        .findById(testSeriesId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Test series not found"
                                )
                        );

        if (Boolean.TRUE.equals(
                testSeries.getResultFinalized()
        )) {

            throw new RuntimeException(
                    "Test series leaderboard already finalized"
            );
        }

        List<TestSeriesExam> seriesExams =
                testSeriesExamRepository
                        .findByTestSeriesIdAndActiveTrueOrderBySequenceAsc(
                                testSeriesId
                        );

        if (seriesExams.isEmpty()) {

            throw new RuntimeException(
                    "No exams found in test series"
            );
        }

        List<LeaderboardResponse> leaderboard =
                buildTestSeriesLeaderboard(
                        seriesExams
                );

        if (leaderboard.isEmpty()) {

            throw new RuntimeException(
                    "No results available"
            );
        }

        /*
         * PDF generation comes next.
         *
         * For now we have successfully calculated
         * the combined leaderboard.
         */

        byte[] pdf =
                leaderboardPdfService
                        .generateTestSeriesLeaderboardPdf(
                                testSeriesId,
                                leaderboard
                        );

        // S3 upload will go here.

        // testSeries.setResultPdfUrl(pdfUrl);
        // testSeries.setResultFinalized(true);

        // testSeriesRepository.save(testSeries);
    }

    @Override
    public byte[] generateExamLeaderboardPdf(
            Long examId
    ) {

        List<Result> results =
                resultRepository
                        .findByExamIdAndPublishedTrueAndActiveTrue(
                                examId
                        );

        if (results.isEmpty()) {

            throw new RuntimeException(
                    "No published results found"
            );
        }

        List<LeaderboardResponse> leaderboard =
                buildFinalExamLeaderboard(results);

        return leaderboardPdfService
                .generateExamLeaderboardPdf(
                        examId,
                        leaderboard
                );
    }

    private List<LeaderboardResponse> buildTestSeriesLeaderboard(
            List<TestSeriesExam> seriesExams
    ) {

        Map<Long, StudentSeriesScore> studentScores =
                new HashMap<>();

        for (TestSeriesExam seriesExam : seriesExams) {

            Long examId =
                    seriesExam.getExam().getId();

            List<Result> results =
                    resultRepository
                            .findByExamIdAndActiveTrue(
                                    examId
                            );

            for (Result result : results) {

                Long studentId =
                        result.getStudent().getId();

                StudentSeriesScore score =
                        studentScores.computeIfAbsent(
                                studentId,
                                id -> new StudentSeriesScore(
                                        result.getStudent()
                                )
                        );

                score.addResult(result);
            }
        }

        List<LeaderboardResponse> leaderboard =
                new ArrayList<>();

        for (StudentSeriesScore score :
                studentScores.values()) {

            double percentage =
                    score.totalMarks == 0
                            ? 0
                            : (score.obtainedMarks * 100.0)
                            / score.totalMarks;

            leaderboard.add(
                    LeaderboardResponse.builder()
                            .studentId(
                                    score.student.getId()
                            )
                            .studentName(
                                    score.student.getStudentName()
                            )
                            .obtainedMarks(
                                    score.obtainedMarks
                            )
                            .totalMarks(
                                    score.totalMarks
                            )
                            .percentage(
                                    percentage
                            )
                            .timeTakenSeconds(
                                    score.totalTimeSeconds
                            )
                            .build()
            );
        }

        /*
         * Highest total marks first.
         *
         * If marks are equal,
         * student who took less time wins.
         */
        leaderboard.sort(
                Comparator
                        .comparing(
                                LeaderboardResponse
                                        ::getObtainedMarks,
                                Comparator.reverseOrder()
                        )
                        .thenComparing(
                                LeaderboardResponse
                                        ::getTimeTakenSeconds
                        )
        );

        /*
         * Assign rank.
         */
        for (int i = 0;
             i < leaderboard.size();
             i++) {

            leaderboard
                    .get(i)
                    .setRank(i + 1);
        }

        return leaderboard;
    }

    private class StudentSeriesScore {

        private final Student student;

        private int obtainedMarks = 0;

        private int totalMarks = 0;

        private long totalTimeSeconds = 0;

        private StudentSeriesScore(
                Student student
        ) {
            this.student = student;
        }

        private void addResult(Result result) {

            obtainedMarks +=
                    result.getObtainedMarks();

            totalMarks +=
                    result.getTotalMarks();

            totalTimeSeconds +=
                    getTimeTaken(result);
        }
    }
}