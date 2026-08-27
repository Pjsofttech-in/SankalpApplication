package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Request.ResultRequest;
import com.sankalpapp.dto.Response.ExamResultResponse;
import com.sankalpapp.dto.mapper.ResultMapper;
import com.sankalpapp.entity.Exam;
import com.sankalpapp.entity.Result;
import com.sankalpapp.entity.Student;
import com.sankalpapp.repository.ExamRepository;
import com.sankalpapp.repository.ResultRepository;
import com.sankalpapp.repository.StudentRepository;
import com.sankalpapp.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;
    private final ResultMapper resultMapper;

    @Override
    public ExamResultResponse saveResult(ResultRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Result result = Result.builder()
                .totalMarks(request.getTotalMarks())
                .obtainedMarks(request.getObtainedMarks())
                .percentage(request.getPercentage())
                .grade(request.getGrade())
                .resultStatus(request.getResultStatus())
                .student(student)
                .exam(exam)
                .build();

        return resultMapper.toResponse(resultRepository.save(result));
    }

    @Override
    public ExamResultResponse updateResult(Long id, ResultRequest request) {

        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found"));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        result.setTotalMarks(request.getTotalMarks());
        result.setObtainedMarks(request.getObtainedMarks());
        result.setPercentage(request.getPercentage());
        result.setGrade(request.getGrade());
        result.setResultStatus(request.getResultStatus());
        result.setStudent(student);
        result.setExam(exam);

        return resultMapper.toResponse(resultRepository.save(result));
    }

    @Override
    public void deleteResult(Long id) {

        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found"));

        resultRepository.delete(result);
    }

    @Override
    public ExamResultResponse getResultById(Long id) {

        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found"));

        return resultMapper.toResponse(result);
    }

    @Override
    public List<ExamResultResponse> getAllResults() {

        return resultRepository.findAll()
                .stream()
                .map(resultMapper::toResponse)
                .collect(Collectors.toList());
    }
}