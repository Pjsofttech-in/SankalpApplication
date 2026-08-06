package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Request.ResultRequest;
import com.sankalpapp.dto.Response.ResultResponse;
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

    @Override
    public ResultResponse saveResult(ResultRequest request) {

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

        return mapToResponse(resultRepository.save(result));
    }

    @Override
    public ResultResponse updateResult(Long id, ResultRequest request) {

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

        return mapToResponse(resultRepository.save(result));
    }

    @Override
    public void deleteResult(Long id) {

        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found"));

        resultRepository.delete(result);
    }

    @Override
    public ResultResponse getResultById(Long id) {

        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found"));

        return mapToResponse(result);
    }

    @Override
    public List<ResultResponse> getAllResults() {

        return resultRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ResultResponse mapToResponse(Result result) {

        return ResultResponse.builder()
                .id(result.getId())
                .studentName(result.getStudent().getStudentName())
                .examName(result.getExam().getExamName())
                .totalMarks(result.getTotalMarks())
                .obtainedMarks(result.getObtainedMarks())
                .percentage(result.getPercentage())
                .grade(result.getGrade())
                .resultStatus(result.getResultStatus())
                .build();
    }
}