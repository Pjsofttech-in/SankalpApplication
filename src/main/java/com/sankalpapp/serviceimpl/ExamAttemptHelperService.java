package com.sankalpapp.serviceimpl;

import com.sankalpapp.entity.*;
import com.sankalpapp.repository.ExamAttemptRepository;
import com.sankalpapp.repository.ExamQuestionRepository;
import com.sankalpapp.repository.ResultRepository;
import com.sankalpapp.repository.StudentAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamAttemptHelperService {

    private final ExamAttemptRepository examAttemptRepository;
    private final ExamQuestionRepository examQuestionRepository;
    private final StudentAnswerRepository studentAnswerRepository;
    private final ResultRepository resultRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void autoSubmitExpiredAttempt(
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Result evaluateAttempt(
            ExamAttempt attempt
    ) {

        Result existingResult =
                resultRepository.findByAttemptId(attempt.getId())
                        .orElse(null);

        if (existingResult != null) {
            return existingResult;
        }

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
                answer.setMarksObtained(0);

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

                int marks = examQuestion.getMarks();

                obtainedMarks += marks;
                answer.setMarksObtained(marks);

            } else {
                incorrectQuestions++;
                answer.setMarksObtained(0);
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
}
