package com.testapplication.serviceimpl;

import com.testapplication.entity.StudentAnswer;
import com.testapplication.repository.StudentAnswerRepository;
import com.testapplication.service.StudentAnswerService;
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
        existing.setStudent(answer.getStudent());
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