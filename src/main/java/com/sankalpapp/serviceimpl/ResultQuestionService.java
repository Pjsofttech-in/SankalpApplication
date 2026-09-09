package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.mapper.ResultMapper;
import com.sankalpapp.dto.response.ExamResultResponse;
import com.sankalpapp.dto.response.ResultQuestionResponse;
import com.sankalpapp.entity.ExamQuestion;
import com.sankalpapp.entity.Question;
import com.sankalpapp.entity.Result;
import com.sankalpapp.entity.StudentAnswer;
import com.sankalpapp.repository.ExamQuestionRepository;
import com.sankalpapp.repository.StudentAnswerRepository;
import com.sankalpapp.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultQuestionService {

    private final ResultMapper resultMapper;
    private final LeaderboardService leaderboardService;
    private final ExamQuestionRepository examQuestionRepository;
    private final StudentAnswerRepository studentAnswerRepository;

    public ExamResultResponse mapWithDetails(Result result) {

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
}
