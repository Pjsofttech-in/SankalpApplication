package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.mapper.ResultMapper;
import com.sankalpapp.dto.request.ResultRequest;
import com.sankalpapp.dto.response.ExamResultResponse;
import com.sankalpapp.dto.response.ResultQuestionResponse;
import com.sankalpapp.entity.*;
import com.sankalpapp.repository.*;
import com.sankalpapp.service.LeaderboardService;
import com.sankalpapp.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final StudentRepository studentRepository;
    private final StudentAnswerRepository studentAnswerRepository;
    private final ExamRepository examRepository;
    private final ExamQuestionRepository examQuestionRepository;
    private final ResultMapper resultMapper;
    private final LeaderboardService leaderboardService;

    @Override
    public ExamResultResponse saveResult(ResultRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Result result = Result.builder()
                .totalMarks(request.getTotalMarks())
                .obtainedMarks(request.getObtainedMarks())
                .percentage(request.getPercentage())
                .grade(request.getGrade())
                .resultStatus(request.getResultStatus())
                .student(student)
                .exam(exam)
                .build();

        return resultMapper.toResponse(resultRepository.save(result));
    }

    @Override
    public ExamResultResponse updateResult(Long id, ResultRequest request) {

        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found"));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        result.setTotalMarks(request.getTotalMarks());
        result.setObtainedMarks(request.getObtainedMarks());
        result.setPercentage(request.getPercentage());
        result.setGrade(request.getGrade());
        result.setResultStatus(request.getResultStatus());
        result.setStudent(student);
        result.setExam(exam);

        return resultMapper.toResponse(resultRepository.save(result));
    }

    @Override
    public void deleteResult(Long id) {

        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found"));

        resultRepository.delete(result);
    }

    @Override
    public List<ExamResultResponse> getAllResults() {

        return resultRepository.findAll()
                .stream()
                .map(this::mapWithDetails)
                .collect(Collectors.toList());
    }

    private ExamResultResponse mapWithDetails(Result result) {

        ExamResultResponse response =
                resultMapper.toResponse(result);

        // Add rank
        if (result.getExam() != null &&
                result.getStudent() != null) {

            Integer rank = leaderboardService.getStudentRank(
                    result.getExam().getId(),
                    result.getStudent().getId()
            );

            response.setRank(rank);
        }

        // Add question-wise result
        response.setQuestions(
                getQuestionResults(result)
        );

        return response;
    }


    private List<ResultQuestionResponse> getQuestionResults(
            Result result) {

        /*
         * Get all questions belonging to this exam
         */
        List<ExamQuestion> examQuestions =
                examQuestionRepository
                        .findByExamOrderBySequenceAsc(
                                result.getExam()
                        );

        /*
         * Get student's answers for this attempt
         */
        List<StudentAnswer> studentAnswers =
                studentAnswerRepository.findByAttempt(
                        result.getAttempt()
                );

        /*
         * Convert answers into Map<QuestionId, StudentAnswer>
         */
        Map<Long, StudentAnswer> answerMap =
                studentAnswers.stream()
                        .collect(Collectors.toMap(
                                answer ->
                                        answer.getQuestion()
                                                .getId(),
                                Function.identity(),
                                (existing, replacement) -> existing
                        ));

        /*
         * Build question-wise response
         */
        return examQuestions.stream()
                .map(examQuestion -> {

                    Question question =
                            examQuestion.getQuestion();

                    StudentAnswer studentAnswer =
                            answerMap.get(question.getId());

                    return ResultQuestionResponse.builder()

                            .questionId(question.getId())

                            .question(question.getQuestion())

                            .optionA(question.getOptionA())
                            .optionB(question.getOptionB())
                            .optionC(question.getOptionC())
                            .optionD(question.getOptionD())

                            .correctAnswer(
                                    question.getCorrectAnswer()
                            )

                            .studentAnswer(
                                    studentAnswer != null
                                            ? studentAnswer.getSelectedAnswer()
                                            : null
                            )

                            .correct(
                                    studentAnswer != null && studentAnswer.getCorrect()
                            )

                            .marks(examQuestion.getMarks())

                            .marksObtained(
                                    studentAnswer != null
                                            ? studentAnswer.getMarksObtained()
                                            : 0
                            )

                            .answerExplanation(
                                    question.getAnswerExplanation()
                            )

                            .build();
                })
                .toList();
    }

    @Override
    public ExamResultResponse getResultById(Long id) {

        Result result = resultRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Result not found"));

        return mapWithDetails(result);
    }
}