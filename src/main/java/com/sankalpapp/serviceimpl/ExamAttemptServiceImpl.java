package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Request.StudentAnswerRequest;
import com.sankalpapp.dto.Response.ExamResultResponse;
import com.sankalpapp.dto.Response.ExamStartResponse;
import com.sankalpapp.dto.Response.StudentQuestionResponse;
import com.sankalpapp.dto.mapper.ResultMapper;
import com.sankalpapp.entity.*;
import com.sankalpapp.repository.*;
import com.sankalpapp.service.ExamAttemptService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamAttemptServiceImpl implements ExamAttemptService {

    private final ExamAttemptRepository examAttemptRepository;
    private final TestSeriesRepository testSeriesRepository;
    private final TestSeriesExamRepository testSeriesExamRepository;
    private final StudentAnswerRepository studentAnswerRepository;
    private final ResultRepository resultRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;
    private final ExamQuestionRepository examQuestionRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final ResultMapper resultMapper;

    @Override
    @Transactional
    public ExamStartResponse startExam(Long examId, Long testSeriesId) {

        Student student = getAuthenticatedStudent();

        Optional<ExamAttempt> activeAttempt;
        if (testSeriesId == null) {
            activeAttempt = examAttemptRepository
                    .findTopByStudentIdAndExamIdAndTestSeriesIsNullAndStatus(
                            student.getId(),
                            examId,
                            ExamAttempt.AttemptStatus.STARTED
                    );
        } else {
            activeAttempt = examAttemptRepository
                    .findTopByStudentIdAndExamIdAndTestSeriesIdAndStatus(
                            student.getId(),
                            examId,
                            testSeriesId,
                            ExamAttempt.AttemptStatus.STARTED
                    );
        }

        if (activeAttempt.isPresent()) {

            ExamAttempt existing =
                    activeAttempt.get();

            if (LocalDateTime.now()
                    .isBefore(existing.getExpiresAt())) {

                return buildExamStartResponse(existing);
            }

            // Existing attempt expired.
            autoSubmitExpiredAttempt(existing);
        }

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new RuntimeException("Exam not found"));

        long attemptCount;
        TestSeries testSeries = null;

        if (testSeriesId != null) {

            testSeries = testSeriesRepository
                    .findById(testSeriesId)
                    .orElseThrow(() ->
                            new RuntimeException("Test Series not found"));

            boolean exists =
                    testSeriesExamRepository
                            .existsByTestSeriesIdAndExamId(
                                    testSeries.getId(),
                                    exam.getId()
                            );

            if (!exists) {
                throw new RuntimeException(
                        "Exam does not belong to this Test Series"
                );
            }

            attemptCount =
                    examAttemptRepository
                            .countByStudentIdAndExamIdAndTestSeriesId(
                                    student.getId(),
                                    exam.getId(),
                                    testSeries.getId()
                            );
        } else {
            attemptCount =
                    examAttemptRepository
                            .countByStudentIdAndExamIdAndTestSeriesIsNull(
                                    student.getId(),
                                    exam.getId()
                            );

        }

        if (!Boolean.TRUE.equals(student.getActive())) {
            throw new RuntimeException(
                    "Student account is inactive"
            );
        }

        if (!Boolean.TRUE.equals(exam.getActive())) {
            throw new RuntimeException(
                    "Exam is inactive"
            );
        }

        if (attemptCount >= exam.getMaxAttempts()) {

            throw new RuntimeException(
                    "Maximum attempts reached for this exam"
            );
        }

        int attemptNumber = (int) attemptCount + 1;

        LocalDateTime startedAt =
                LocalDateTime.now();

        LocalDateTime expiresAt =
                startedAt.plusMinutes(
                        exam.getDuration()
                );

        ExamAttempt attempt =
                ExamAttempt.builder()
                        .student(student)
                        .exam(exam)
                        .testSeries(testSeries)
                        .attemptNumber(attemptNumber)
                        .startedAt(startedAt)
                        .expiresAt(expiresAt)
                        .status(
                                ExamAttempt.AttemptStatus.STARTED
                        )
                        .build();

        validateExamStartTime(attempt);

        ExamAttempt savedAttempt =
                examAttemptRepository.save(attempt);

        return ExamStartResponse.builder()
                .attemptId(savedAttempt.getId())
                .examId(exam.getId())
                .examName(exam.getExamName())
                .startedAt(startedAt)
                .expiresAt(expiresAt)
                .duration(exam.getDuration())
                .totalQuestions(exam.getTotalQuestions())
                .totalMarks(exam.getTotalMarks())
                .status(
                        savedAttempt
                                .getStatus()
                                .name()
                )
                .build();
    }

    @Override
    @Transactional
    public List<StudentQuestionResponse> getExamQuestions(
            Long attemptId
    ) {

        ExamAttempt attempt = getAttempt(attemptId);

        if (attempt.getStatus() !=
                ExamAttempt.AttemptStatus.STARTED) {

            throw new RuntimeException(
                    "Exam attempt is no longer active"
            );
        }

        List<ExamQuestion> examQuestions =
                examQuestionRepository
                        .findByExamOrderBySequenceAsc(
                                attempt.getExam()
                        );

        return examQuestions.stream()
                .filter(
                        eq ->
                                Boolean.TRUE.equals(
                                        eq.getActive()
                                )
                )
                .map(eq ->
                        StudentQuestionResponse.builder()
                                .questionId(
                                        eq.getQuestion().getId()
                                )
                                .question(
                                        eq.getQuestion().getQuestion()
                                )
                                .optionA(
                                        eq.getQuestion().getOptionA()
                                )
                                .optionB(
                                        eq.getQuestion().getOptionB()
                                )
                                .optionC(
                                        eq.getQuestion().getOptionC()
                                )
                                .optionD(
                                        eq.getQuestion().getOptionD()
                                )
                                .marks(eq.getMarks())
                                .sequence(eq.getSequence())
                                .build()
                )
                .toList();
    }

    @Override
    @Transactional
    public void saveAnswer(
            Long attemptId,
            StudentAnswerRequest request
    ) {

        ExamAttempt attempt =
                getStudentAttempt(attemptId);

        validateExamStartTime(attempt);
        validateExamTime(attempt);

        if (attempt.getStatus()
                != ExamAttempt.AttemptStatus.STARTED) {

            throw new RuntimeException(
                    "Exam is not active"
            );
        }

        Question question =
                questionRepository
                        .findById(
                                request.getQuestionId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Question not found"
                                )
                        );

        /*
         * Verify that this question actually
         * belongs to the exam being attempted.
         */
        examQuestionRepository
                .findByExamIdAndQuestionId(
                        attempt.getExam().getId(),
                        question.getId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Question does not belong to this exam"
                        )
                );

        /*
         * Find existing answer.
         *
         * If the student has already answered
         * this question, update it.
         *
         * Otherwise create a new answer.
         */
        StudentAnswer answer =
                studentAnswerRepository
                        .findByAttemptIdAndQuestionId(
                                attemptId,
                                request.getQuestionId()
                        )
                        .orElse(
                                StudentAnswer.builder()
                                        .attempt(attempt)
                                        .question(question)
                                        .build()
                        );

        /*
         * Save selected answer.
         *
         * Do NOT calculate correctness here.
         */
        answer.setSelectedAnswer(
                request.getSelectedAnswer()
        );

        answer.setCorrect(false);

        studentAnswerRepository.save(answer);
    }

    @Override
    @Transactional
    public ExamResultResponse submitExam(
            Long attemptId
    ) {
        ExamAttempt attempt =
                getStudentAttempt(attemptId);

        if (attempt.getStatus()
                != ExamAttempt.AttemptStatus.STARTED) {

            throw new RuntimeException(
                    "Exam has already been submitted"
            );
        }
        validateExamStartTime(attempt);
        validateExamTime(attempt);
        attempt.setStatus(
                ExamAttempt.AttemptStatus.SUBMITTED
        );

        attempt.setSubmittedAt(
                LocalDateTime.now()
        );

        examAttemptRepository.save(attempt);

        Result result =
                evaluateAttempt(attempt);

        return resultMapper.toResponse(result);
    }

    @Override
    @Transactional
    public ExamResultResponse getResult(
            Long attemptId
    ) {

        Result result =
                resultRepository
                        .findByAttemptId(attemptId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Result not found for attempt: "
                                                + attemptId
                                )
                        );

        /*
         * Don't allow students to see an unpublished result.
         */
        if (!Boolean.TRUE.equals(
                result.getPublished()
        )) {

            throw new RuntimeException(
                    "Result has not been published yet"
            );
        }

        return resultMapper.toResponse(result);
    }

    @Override
    @Transactional
    public ExamResultResponse publishResult(
            Long resultId
    ) {

        Result result =
                resultRepository
                        .findById(resultId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Result not found with id: "
                                                + resultId
                                )
                        );

        result.setPublished(true);

        result.getAttempt()
                .setStatus(
                        ExamAttempt.AttemptStatus.PUBLISHED
                );

        Result savedResult =
                resultRepository.save(result);

        return resultMapper.toResponse(
                savedResult
        );
    }

    private ExamAttempt getAttempt(
            Long attemptId
    ) {

        return examAttemptRepository
                .findById(attemptId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exam attempt not found with id: "
                                        + attemptId
                        )
                );
    }

    private void validateAttemptIsActive(
            ExamAttempt attempt
    ) {

        if (attempt.getStatus() !=
                ExamAttempt.AttemptStatus.STARTED) {

            throw new RuntimeException(
                    "Exam attempt is not active"
            );
        }
    }

    private String calculateGrade(
            double percentage
    ) {

        if (percentage >= 90) {
            return "A+";
        }

        if (percentage >= 80) {
            return "A";
        }

        if (percentage >= 70) {
            return "B";
        }

        if (percentage >= 60) {
            return "C";
        }

        if (percentage >= 50) {
            return "D";
        }

        if (percentage >= 35) {
            return "E";
        }

        return "F";
    }

    private void validateExamTime(
            ExamAttempt attempt
    ) {
        LocalDateTime now = LocalDateTime.now();
        boolean examEndTimeCheck = false;
        boolean testSeriesEndTimeCheck = false;
        if (attempt.getStatus()
                != ExamAttempt.AttemptStatus.STARTED) {

            throw new RuntimeException(
                    "Exam attempt is no longer active"
            );
        }

        if (attempt.getExam() != null && attempt.getExam().getTestEndDate() != null
                && attempt.getExam().getEndTime() != null) {
            examEndTimeCheck = now.isAfter(LocalDateTime.of(attempt.getExam().getTestEndDate(),
                    attempt.getExam().getEndTime()));
        }

        if (attempt.getTestSeries() != null && attempt.getTestSeries().getEndDate() != null) {
            testSeriesEndTimeCheck = now.isAfter(LocalDateTime.of(attempt.getTestSeries().getEndDate(),
                    LocalTime.MAX));
        }

        if (now.isAfter(attempt.getExpiresAt())
                || examEndTimeCheck || testSeriesEndTimeCheck) {

            autoSubmitExpiredAttempt(attempt);

            throw new RuntimeException(
                    "Exam time has expired"
            );
        }
    }

    private void validateExamStartTime(
            ExamAttempt attempt
    ) {
        LocalDateTime now = LocalDateTime.now();
        boolean examStartTimeCheck = false;
        boolean testSeriesStartTimeCheck = false;
        if (attempt.getStatus()
                != ExamAttempt.AttemptStatus.STARTED) {

            throw new RuntimeException(
                    "Exam attempt is no longer active"
            );
        }

        if (attempt.getExam() != null && attempt.getExam().getTestStartDate() != null
                && attempt.getExam().getStartTime() != null) {
            examStartTimeCheck = now.isBefore(LocalDateTime.of(attempt.getExam().getTestStartDate(),
                    attempt.getExam().getStartTime()));
        }

        if (attempt.getTestSeries() != null && attempt.getTestSeries().getStartDate() != null) {
            testSeriesStartTimeCheck = now.isBefore(LocalDateTime.of(attempt.getTestSeries().getStartDate(),
                    LocalTime.MIDNIGHT));
        }

        if (examStartTimeCheck || testSeriesStartTimeCheck) {

            throw new RuntimeException(
                    "Exam/TestSeries time has not started yet"
            );
        }
    }

    private void autoSubmitExpiredAttempt(
            ExamAttempt attempt
    ) {

        if (attempt.getStatus()
                != ExamAttempt.AttemptStatus.STARTED) {

            return;
        }

        attempt.setStatus(
                ExamAttempt.AttemptStatus.SUBMITTED
        );

        attempt.setSubmittedAt(
                attempt.getExpiresAt()
        );

        examAttemptRepository.save(attempt);

        evaluateAttempt(attempt);
    }

    private Result evaluateAttempt(
            ExamAttempt attempt
    ) {

        Exam exam = attempt.getExam();

        List<StudentAnswer> answers =
                studentAnswerRepository
                        .findByAttempt(attempt);

        /*
         * Question statistics
         */
        int correctQuestions = 0;
        int incorrectQuestions = 0;
        int solvedQuestions = 0;

        /*
         * Marks
         */
        int totalMarks = 0;
        int obtainedMarks = 0;

        /*
         * Get all questions assigned to this exam.
         */
        List<ExamQuestion> examQuestions =
                examQuestionRepository
                        .findByExamOrderBySequenceAsc(
                                exam
                        );

        /*
         * Calculate total marks from ExamQuestion.
         */
        totalMarks = examQuestions.stream().mapToInt(ExamQuestion::getMarks).sum();

        /*
         * Evaluate student's answers.
         */
        for (StudentAnswer answer : answers) {

            String selectedAnswer =
                    answer.getSelectedAnswer();

            /*
             * Unanswered question
             */
            if (selectedAnswer == null ||
                    selectedAnswer.trim().isEmpty()) {

                answer.setCorrect(false);

                continue;
            }

            solvedQuestions++;

            Question question =
                    answer.getQuestion();

            boolean correct =
                    question.getCorrectAnswer()
                            .equalsIgnoreCase(
                                    selectedAnswer.trim()
                            );

            answer.setCorrect(correct);

            if (correct) {

                correctQuestions++;

                /*
                 * Find marks assigned to this question
                 * in this particular exam.
                 */
                ExamQuestion examQuestion =
                        examQuestions.stream()
                                .filter(eq ->
                                        eq.getQuestion()
                                                .getId()
                                                .equals(
                                                        question.getId()
                                                )
                                )
                                .findFirst()
                                .orElseThrow(() ->
                                        new RuntimeException(
                                                "Question is not assigned to this exam"
                                        )
                                );

                obtainedMarks +=
                        examQuestion.getMarks();

            } else {

                incorrectQuestions++;
            }
        }

        /*
         * Calculate unsolved questions.
         */
        int unsolvedQuestions =
                exam.getTotalQuestions()
                        - solvedQuestions;

        /*
         * Prevent negative value if data is inconsistent.
         */
        if (unsolvedQuestions < 0) {
            unsolvedQuestions = 0;
        }

        /*
         * Save evaluated answers.
         */
        studentAnswerRepository.saveAll(answers);

        /*
         * Calculate percentage.
         */
        double percentage =
                totalMarks == 0
                        ? 0
                        : (obtainedMarks * 100.0)
                        / totalMarks;

        /*
         * Grade.
         */
        String grade =
                calculateGrade(percentage);

        /*
         * Result status.
         */
        String resultStatus =
                percentage >= 35
                        ? "PASS"
                        : "FAIL";

        /*
         * Create Result.
         */
        Result result =
                Result.builder()

                        .student(
                                attempt.getStudent()
                        )

                        .exam(exam)

                        .attempt(attempt)

                        .totalMarks(totalMarks)

                        .obtainedMarks(obtainedMarks)

                        .percentage(percentage)

                        .grade(grade)

                        .resultStatus(resultStatus)

                        .correctQuestions(
                                correctQuestions
                        )

                        .incorrectQuestions(
                                incorrectQuestions
                        )

                        .solvedQuestions(
                                solvedQuestions
                        )

                        .unsolvedQuestions(
                                unsolvedQuestions
                        )

                        .published(false)

                        .active(true)

                        .build();

        /*
         * Result is now evaluated.
         */
        attempt.setStatus(
                ExamAttempt.AttemptStatus.EVALUATED
        );

        examAttemptRepository.save(attempt);

        return resultRepository.save(result);
    }

    private ExamAttempt getStudentAttempt(
            Long attemptId
    ) {

        ExamAttempt attempt =
                examAttemptRepository
                        .findById(attemptId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Exam attempt not found"
                                )
                        );

        Student authenticatedStudent =
                getAuthenticatedStudent();

        if (!attempt.getStudent()
                .getId()
                .equals(authenticatedStudent.getId())) {

            throw new RuntimeException(
                    "You are not authorized to access this exam attempt"
            );
        }

        return attempt;
    }

    private Student getAuthenticatedStudent() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new RuntimeException(
                    "User is not authenticated"
            );
        }

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Authenticated user not found"
                        )
                );

        return studentRepository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student profile not found"
                        )
                );
    }

    private ExamStartResponse buildExamStartResponse(
            ExamAttempt attempt
    ) {

        Exam exam = attempt.getExam();

        return ExamStartResponse.builder()
                .attemptId(attempt.getId())
                .examId(exam.getId())
                .examName(exam.getExamName())
                .startedAt(attempt.getStartedAt())
                .expiresAt(attempt.getExpiresAt())
                .duration(exam.getDuration())
                .totalQuestions(exam.getTotalQuestions())
                .totalMarks(exam.getTotalMarks())
                .status(
                        attempt.getStatus().name()
                )
                .build();
    }
}