package com.sankalpapp.serviceimpl;

import com.sankalpapp.entity.Question;
import com.sankalpapp.repository.QuestionRepository;
import com.sankalpapp.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(
            QuestionRepository questionRepository
    ) {
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional
    public Question create(Question question) {

        question.setId(null);

        if (question.getActive() == null) {
            question.setActive(true);
        }

        return questionRepository.save(question);
    }

    @Override
    @Transactional(readOnly = true)
    public Question getById(Long id) {

        return questionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Question not found with id: " + id
                        )
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> getAll() {

        return questionRepository.findAll();
    }

    @Override
    @Transactional
    public Question update(
            Long id,
            Question question
    ) {

        Question existing = getById(id);

        existing.setQuestion(question.getQuestion());
        existing.setOptionA(question.getOptionA());
        existing.setOptionB(question.getOptionB());
        existing.setOptionC(question.getOptionC());
        existing.setOptionD(question.getOptionD());
        existing.setCorrectAnswer(question.getCorrectAnswer());

        if (question.getActive() != null) {
            existing.setActive(question.getActive());
        }

        return questionRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Question question = getById(id);

        questionRepository.delete(question);
    }
}