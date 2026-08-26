package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Request.QuestionRequest;
import com.sankalpapp.dto.Response.QuestionResponse;
import com.sankalpapp.dto.mapper.QuestionMapper;
import com.sankalpapp.entity.Question;
import com.sankalpapp.repository.QuestionRepository;
import com.sankalpapp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;


    @Override
    @Transactional
    public QuestionResponse create(QuestionRequest questionRequest) {

        Question question = new Question();
        updateEntityFromRequest(question, questionRequest);

        return questionMapper.toResponse(questionRepository.save(question));
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionResponse getById(Long id) {

        return questionMapper.toResponse(questionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Question not found with id: " + id
                        )
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionResponse> getAll() {

        return questionRepository.findAll().stream().map(questionMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public QuestionResponse update(
            Long id,
            QuestionRequest questionRequest
    ) {

        Question question = questionRepository.findById(id).orElseThrow();
        updateEntityFromRequest(question, questionRequest);
        return questionMapper.toResponse(questionRepository.save(question));
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Question question = questionRepository.findById(id).orElseThrow();

        questionRepository.delete(question);
    }

    /**
     * Updates an existing Question entity with non-null values from the request.
     */
    public void updateEntityFromRequest(Question entity, QuestionRequest request) {
        if (entity == null || request == null) {
            return;
        }

        if (StringUtils.hasText(request.getQuestion())) {
            entity.setQuestion(request.getQuestion());
        }
        if (StringUtils.hasText(request.getQuestionType())) {
            entity.setQuestionType(request.getQuestionType());
        }
        if (StringUtils.hasText(request.getOptionA())) {
            entity.setOptionA(request.getOptionA());
        }
        if (StringUtils.hasText(request.getOptionB())) {
            entity.setOptionB(request.getOptionB());
        }
        if (StringUtils.hasText(request.getOptionC())) {
            entity.setOptionC(request.getOptionC());
        }
        if (StringUtils.hasText(request.getOptionD())) {
            entity.setOptionD(request.getOptionD());
        }
        if (StringUtils.hasText(request.getCorrectAnswer())) {
            entity.setCorrectAnswer(request.getCorrectAnswer());
        }
        if (StringUtils.hasText(request.getAnswerExplanation())) {
            entity.setAnswerExplanation(request.getAnswerExplanation());
        }
        if (request.getActive() != null) {
            entity.setActive(request.getActive());
        }
    }
}