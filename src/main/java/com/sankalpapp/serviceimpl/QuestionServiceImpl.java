package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Request.QuestionRequest;
import com.sankalpapp.dto.Response.QuestionResponse;
import com.sankalpapp.entity.Exam;
import com.sankalpapp.entity.Question;
import com.sankalpapp.repository.ExamRepository;
import com.sankalpapp.repository.QuestionRepository;
import com.sankalpapp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    @Override
    public QuestionResponse saveQuestion(QuestionRequest request) {

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found."));

        Question question = Question.builder()
                .question(request.getQuestion())
                .optionA(request.getOptionA())
                .optionB(request.getOptionB())
                .optionC(request.getOptionC())
                .optionD(request.getOptionD())
                .correctAnswer(request.getCorrectAnswer())
                .marks(request.getMarks())
                .exam(exam)
                .build();

        return mapToResponse(questionRepository.save(question));
    }

    @Override
    public QuestionResponse updateQuestion(Long id, QuestionRequest request) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found."));

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found."));

        question.setQuestion(request.getQuestion());
        question.setOptionA(request.getOptionA());
        question.setOptionB(request.getOptionB());
        question.setOptionC(request.getOptionC());
        question.setOptionD(request.getOptionD());
        question.setCorrectAnswer(request.getCorrectAnswer());
        question.setMarks(request.getMarks());
        question.setExam(exam);

        return mapToResponse(questionRepository.save(question));
    }

    @Override
    public void deleteQuestion(Long id) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found."));

        questionRepository.delete(question);
    }

    @Override
    public QuestionResponse getQuestionById(Long id) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found."));

        return mapToResponse(question);
    }

    @Override
    public List<QuestionResponse> getAllQuestions() {

        return questionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private QuestionResponse mapToResponse(Question question) {

        return QuestionResponse.builder()
                .id(question.getId())
                .question(question.getQuestion())
                .optionA(question.getOptionA())
                .optionB(question.getOptionB())
                .optionC(question.getOptionC())
                .optionD(question.getOptionD())
                .correctAnswer(question.getCorrectAnswer())
                .marks(question.getMarks())
                .examId(question.getExam().getId())
                .examName(question.getExam().getExamName())
                .build();
    }
}