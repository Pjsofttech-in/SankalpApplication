package com.testapplication.serviceimpl;

import com.testapplication.entity.AnswerKey;
import com.testapplication.repository.AnswerKeyRepository;
import com.testapplication.service.AnswerKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerKeyServiceImpl implements AnswerKeyService {

    private final AnswerKeyRepository repository;

    @Override
    public AnswerKey saveAnswerKey(AnswerKey answerKey) {
        return repository.save(answerKey);
    }

    @Override
    public AnswerKey updateAnswerKey(Long id, AnswerKey answerKey) {

        AnswerKey existing = getAnswerKeyById(id);

        existing.setTitle(answerKey.getTitle());
        existing.setFileName(answerKey.getFileName());
        existing.setFilePath(answerKey.getFilePath());
        existing.setExam(answerKey.getExam());
        existing.setActive(answerKey.getActive());

        return repository.save(existing);
    }

    @Override
    public void deleteAnswerKey(Long id) {
        repository.delete(getAnswerKeyById(id));
    }

    @Override
    public AnswerKey getAnswerKeyById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer Key not found."));
    }

    @Override
    public List<AnswerKey> getAllAnswerKeys() {
        return repository.findAll();
    }
}