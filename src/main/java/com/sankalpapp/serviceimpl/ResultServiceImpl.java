package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.response.ExamResultResponse;
import com.sankalpapp.repository.ResultRepository;
import com.sankalpapp.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final ResultQuestionService resultQuestionService;

    @Override
    public List<ExamResultResponse> getAllResults() {

        return resultRepository.findAll()
                .stream()
                .map(resultQuestionService::mapWithDetails)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExamResultResponse> getAllResultsByStudentId(Long studentId) {

        return resultRepository.findByStudentIdAndActiveTrue(studentId)
                .stream()
                .map(resultQuestionService::mapWithDetails)
                .collect(Collectors.toList());
    }
}