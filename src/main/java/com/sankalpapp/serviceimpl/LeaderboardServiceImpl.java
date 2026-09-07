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
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
                        .findByExamIdAndActiveTrue(
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

                            .correctQuestions(result.getCorrectQuestions())
                            .incorrectQuestions(result.getIncorrectQuestions())
                            .solvedQuestions(result.getSolvedQuestions())
                            .unsolvedQuestions(result.getUnsolvedQuestions())

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
    public List<LeaderboardResponse> getTestSeriesLeaderboard(
            Long testSeriesId) {

        // 1. Find test series
        TestSeries testSeries = testSeriesRepository.findById(testSeriesId)
                .orElseThrow(() ->
                        new RuntimeException("Test series not found"));


        // 2. Get active exams belonging to this test series
        List<TestSeriesExam> testSeriesExams =
                testSeries.getExams()
                        .stream()
                        .filter(exam -> Boolean.TRUE.equals(exam.getActive()))
                        .sorted(
                                Comparator.comparing(
                                        TestSeriesExam::getSequence
                                )
                        )
                        .toList();


        // 3. No exams -> empty leaderboard
        if (testSeriesExams.isEmpty()) {
            return Collections.emptyList();
        }


        /*
         * studentId -> List of best Result for each exam
         */
        Map<Long, List<Result>> studentResults = new HashMap<>();


        // 4. Process every exam in the test series
        for (TestSeriesExam testSeriesExam : testSeriesExams) {

            Long examId = testSeriesExam.getExam().getId();


            /*
             * Get only published and active results.
             */
            List<Result> results =
                    resultRepository
                            .findByExamIdAndActiveTrue(
                                    examId
                            );


            /*
             * Keep the BEST attempt for each student.
             *
             * Priority:
             * 1. Higher marks
             * 2. Lower time if marks are equal
             */
            Map<Long, Result> bestExamResults =
                    results.stream()
                            .collect(Collectors.toMap(
                                    result ->
                                            result.getStudent().getId(),

                                    Function.identity(),

                                    (r1, r2) -> {

                                        /*
                                         * Higher marks wins
                                         */
                                        if (!r1.getObtainedMarks()
                                                .equals(r2.getObtainedMarks())) {

                                            return r1.getObtainedMarks()
                                                    > r2.getObtainedMarks()
                                                    ? r1
                                                    : r2;
                                        }


                                        /*
                                         * Same marks:
                                         * lower time wins
                                         */
                                        long time1 = getTimeTaken(r1);
                                        long time2 = getTimeTaken(r2);

                                        return time1 <= time2
                                                ? r1
                                                : r2;
                                    }
                            ));


            /*
             * Add the best result of this exam
             * to each student's overall results.
             */
            for (Result result : bestExamResults.values()) {

                Long studentId =
                        result.getStudent().getId();

                studentResults
                        .computeIfAbsent(
                                studentId,
                                _ -> new ArrayList<>()
                        )
                        .add(result);
            }
        }


        /*
         * 5. Create leaderboard entries
         */
        List<LeaderboardResponse> leaderboard =
                studentResults.entrySet()
                        .stream()
                        .map(entry -> {

                            Long studentId = entry.getKey();

                            List<Result> results = entry.getValue();


                            /*
                             * Total marks possible
                             */
                            int totalMarks = results.stream()
                                    .mapToInt(Result::getTotalMarks)
                                    .sum();


                            /*
                             * Total marks obtained
                             */
                            int obtainedMarks = results.stream()
                                    .mapToInt(Result::getObtainedMarks)
                                    .sum();


                            /*
                             * Total time taken across all exams
                             */
                            long totalTimeTaken = results.stream()
                                    .mapToLong(this::getTimeTaken)
                                    .sum();

                            /*
                             * Correct questions.
                             */
                            int correctQuestions =
                                    results.stream()
                                            .mapToInt(
                                                    result ->
                                                            result.getCorrectQuestions() != null
                                                                    ? result.getCorrectQuestions()
                                                                    : 0
                                            )
                                            .sum();


                            /*
                             * Incorrect questions.
                             */
                            int incorrectQuestions =
                                    results.stream()
                                            .mapToInt(
                                                    result ->
                                                            result.getIncorrectQuestions() != null
                                                                    ? result.getIncorrectQuestions()
                                                                    : 0
                                            )
                                            .sum();


                            /*
                             * Solved questions.
                             */
                            int solvedQuestions =
                                    results.stream()
                                            .mapToInt(
                                                    result ->
                                                            result.getSolvedQuestions() != null
                                                                    ? result.getSolvedQuestions()
                                                                    : 0
                                            )
                                            .sum();


                            /*
                             * Unsolved questions.
                             */
                            int unsolvedQuestions =
                                    results.stream()
                                            .mapToInt(
                                                    result ->
                                                            result.getUnsolvedQuestions() != null
                                                                    ? result.getUnsolvedQuestions()
                                                                    : 0
                                            )
                                            .sum();

                            /*
                             * Overall percentage
                             */
                            double percentage =
                                    totalMarks == 0
                                            ? 0.0
                                            : (obtainedMarks * 100.0)
                                            / totalMarks;


                            /*
                             * Student name
                             */
                            String studentName =
                                    results.getFirst()
                                            .getStudent()
                                            .getFullName();


                            return LeaderboardResponse.builder()
                                    .studentId(studentId)
                                    .studentName(studentName)
                                    .totalMarks(totalMarks)
                                    .obtainedMarks(obtainedMarks)
                                    .percentage(percentage)
                                    .timeTakenSeconds(totalTimeTaken)
                                    .correctQuestions(correctQuestions)
                                    .incorrectQuestions(incorrectQuestions)
                                    .solvedQuestions(solvedQuestions)
                                    .unsolvedQuestions(unsolvedQuestions)
                                    .build();
                        })
                        .sorted(
                                Comparator
                                        /*
                                         * Higher marks first
                                         */
                                        .comparing(
                                                LeaderboardResponse::
                                                        getObtainedMarks,
                                                Comparator.reverseOrder()
                                        )

                                        /*
                                         * Same marks:
                                         * lower time first
                                         */
                                        .thenComparing(
                                                LeaderboardResponse::
                                                        getTimeTakenSeconds
                                        )
                        )
                        .collect(Collectors.toList());


        /*
         * 6. Competition ranking
         *
         * Example:
         *
         * Marks
         * 100 -> Rank 1
         * 95  -> Rank 2
         * 95  -> Rank 2
         * 90  -> Rank 4
         */
        int rank = 0;
        Integer previousMarks = null;

        for (int i = 0; i < leaderboard.size(); i++) {

            LeaderboardResponse current =
                    leaderboard.get(i);


            if (!current.getObtainedMarks()
                    .equals(previousMarks)) {

                rank = i + 1;

                previousMarks =
                        current.getObtainedMarks();
            }


            current.setRank(rank);
        }


        return leaderboard;
    }

    private long getTimeTaken(Result result) {

        if (result.getAttempt() == null ||
                result.getAttempt().getStartedAt() == null ||
                result.getAttempt().getSubmittedAt() == null) {

            return Long.MAX_VALUE;
        }

        return java.time.Duration.between(
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
                        .findByExamIdAndActiveTrue(
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
         * For now, we have successfully calculated
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
                        .findByExamIdAndActiveTrue(
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
                                _ -> new StudentSeriesScore(
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

    @Override
    public Integer getStudentRank(Long examId, Long studentId) {

        List<Result> results =
                resultRepository.findByExamIdAndActiveTrue(examId);

        // Keep the best result for each student
        Map<Long, Result> bestResults = new HashMap<>();

        for (Result result : results) {

            Long currentStudentId = result.getStudent().getId();

            Result existing = bestResults.get(currentStudentId);

            if (existing == null) {
                bestResults.put(currentStudentId, result);
                continue;
            }

            boolean betterMarks =
                    result.getObtainedMarks() > existing.getObtainedMarks();

            boolean sameMarks =
                    result.getObtainedMarks().equals(existing.getObtainedMarks());

            long resultTime = getTimeTaken(result);
            long existingTime = getTimeTaken(existing);

            boolean betterTime =
                    sameMarks && resultTime < existingTime;

            if (betterMarks || betterTime) {
                bestResults.put(currentStudentId, result);
            }
        }

        List<Result> rankedResults = new ArrayList<>(bestResults.values());

        rankedResults.sort((r1, r2) -> {

            int marksComparison =
                    Integer.compare(
                            r2.getObtainedMarks(),
                            r1.getObtainedMarks()
                    );

            if (marksComparison != 0) {
                return marksComparison;
            }

            return Long.compare(
                    getTimeTaken(r1),
                    getTimeTaken(r2)
            );
        });

        int rank = 0;
        int previousMarks = -1;

        for (int i = 0; i < rankedResults.size(); i++) {

            Result result = rankedResults.get(i);

            if (result.getObtainedMarks() != previousMarks) {
                rank = i + 1;
                previousMarks = result.getObtainedMarks();
            }

            if (result.getStudent().getId().equals(studentId)) {
                return rank;
            }
        }

        return null;
    }
}