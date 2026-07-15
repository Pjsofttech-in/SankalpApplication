package com.testapplication.serviceimpl;

import com.testapplication.entity.Question;
import com.testapplication.repository.QuestionRepository;
import com.testapplication.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Long id, Question question) {

        Question existing = getQuestionById(id);

        existing.setQuestion(question.getQuestion());
        existing.setOptionA(question.getOptionA());
        existing.setOptionB(question.getOptionB());
        existing.setOptionC(question.getOptionC());
        existing.setOptionD(question.getOptionD());
        existing.setCorrectAnswer(question.getCorrectAnswer());
        existing.setMarks(question.getMarks());
        existing.setExam(question.getExam());

        return questionRepository.save(existing);
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.delete(getQuestionById(id));
    }

    @Override
    public Question getQuestionById(Long id) {

        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found."));
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
}