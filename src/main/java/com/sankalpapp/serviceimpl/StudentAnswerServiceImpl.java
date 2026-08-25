package com.sankalpapp.serviceimpl;

import com.sankalpapp.entity.StudentAnswer;
import com.sankalpapp.repository.StudentAnswerRepository;
import com.sankalpapp.service.StudentAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentAnswerServiceImpl implements StudentAnswerService {

    private final StudentAnswerRepository repository;

    @Override
    public StudentAnswer saveAnswer(StudentAnswer answer) {
        return repository.save(answer);
    }

    @Override
    public StudentAnswer updateAnswer(Long id, StudentAnswer answer) {

        StudentAnswer existing = getAnswerById(id);

        existing.setSelectedAnswer(answer.getSelectedAnswer());
        existing.setCorrect(answer.getCorrect());
        existing.setAttempt(answer.getAttempt());
        existing.setQuestion(answer.getQuestion());

        return repository.save(existing);
    }

    @Override
    public void deleteAnswer(Long id) {
        repository.delete(getAnswerById(id));
    }

    @Override
    public StudentAnswer getAnswerById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found."));
    }

    @Override
    public List<StudentAnswer> getAllAnswers() {
        return repository.findAll();
    }
}