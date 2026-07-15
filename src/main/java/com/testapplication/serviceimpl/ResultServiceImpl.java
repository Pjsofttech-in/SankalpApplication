package com.testapplication.serviceimpl;

import com.testapplication.entity.Result;
import com.testapplication.repository.ResultRepository;
import com.testapplication.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository repository;

    @Override
    public Result saveResult(Result result) {
        return repository.save(result);
    }

    @Override
    public Result updateResult(Long id, Result result) {

        Result existing = getResultById(id);

        existing.setObtainedMarks(result.getObtainedMarks());
        existing.setTotalMarks(result.getTotalMarks());
        existing.setPercentage(result.getPercentage());
        existing.setGrade(result.getGrade());
        existing.setResultStatus(result.getResultStatus());
        existing.setStudent(result.getStudent());
        existing.setExam(result.getExam());

        return repository.save(existing);
    }

    @Override
    public void deleteResult(Long id) {
        repository.delete(getResultById(id));
    }

    @Override
    public Result getResultById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found."));
    }

    @Override
    public List<Result> getAllResults() {
        return repository.findAll();
    }
}