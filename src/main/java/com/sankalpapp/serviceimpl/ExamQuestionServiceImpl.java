package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.mapper.ExamQuestionMapper;
import com.sankalpapp.dto.response.ExamQuestionDto;
import com.sankalpapp.entity.Exam;
import com.sankalpapp.entity.ExamQuestion;
import com.sankalpapp.entity.Question;
import com.sankalpapp.repository.ExamQuestionRepository;
import com.sankalpapp.repository.ExamRepository;
import com.sankalpapp.repository.QuestionRepository;
import com.sankalpapp.service.ExamQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamQuestionServiceImpl implements ExamQuestionService {

    private final ExamQuestionRepository examQuestionRepository;
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final ExamQuestionMapper examQuestionMapper;

    @Override
    @Transactional
    public ExamQuestionDto addQuestion(
            Long examId,
            Long questionId,
            Integer sequence,
            Integer marks
    ) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new RuntimeException("Exam not found"));

        long questionCount =
                examQuestionRepository.countByExamIdAndActiveTrue(exam.getId());

        if (questionCount >= exam.getTotalQuestions()) {
            throw new RuntimeException(
                    "Exam already contains maximum allowed questions: "
                            + exam.getTotalQuestions()
            );
        }

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Question not found with id: " + questionId
                        )
                );

        if (examQuestionRepository
                .findByExamAndQuestion(exam, question)
                .isPresent()) {

            throw new RuntimeException(
                    "Question is already added to this exam"
            );
        }

        if (sequence == null) {

            List<ExamQuestion> existing =
                    examQuestionRepository
                            .findByExamOrderBySequenceAsc(exam);

            sequence = existing.size() + 1;
        }

        if (marks == null || marks <= 0) {
            throw new RuntimeException(
                    "Marks must be greater than zero"
            );
        }

        if (sequence > exam.getTotalQuestions()) {
            throw new RuntimeException(
                    "Sequence cannot be greater than total questions: "
                            + exam.getTotalQuestions()
            );
        }

        boolean existsSequence = examQuestionRepository.existsByExamIdAndSequenceAndActiveTrue(examId, sequence);

        if (existsSequence) {
            throw new RuntimeException(
                    "Another question already exists for the same sequence"
            );
        }

        if (sequence < 1) {
            throw new RuntimeException(
                    "Sequence must be greater than 0"
            );
        }

        long currentMarks =
                examQuestionRepository
                        .sumMarksByExamId(exam.getId());

        if (currentMarks + marks > exam.getTotalMarks()) {
            throw new RuntimeException(
                    "Total question marks cannot exceed exam total marks"
            );
        }

        ExamQuestion examQuestion = ExamQuestion.builder()
                .exam(exam)
                .question(question)
                .sequence(sequence)
                .marks(marks)
                .active(true)
                .build();

        return examQuestionMapper.toDto(examQuestionRepository.save(examQuestion));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamQuestionDto> getQuestionsByExam(
            Long examId
    ) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exam not found with id: " + examId
                        )
                );

        return examQuestionRepository
                .findByExamOrderBySequenceAsc(exam).stream().map(examQuestionMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void removeQuestion(
            Long examId,
            Long questionId
    ) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exam not found with id: " + examId
                        )
                );

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Question not found with id: " + questionId
                        )
                );

        ExamQuestion examQuestion =
                examQuestionRepository
                        .findByExamAndQuestion(exam, question)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Question is not part of this exam"
                                )
                        );

        examQuestionRepository.delete(examQuestion);
    }

    @Override
    @Transactional
    public ExamQuestionDto updateSequence(
            Long examId,
            Long questionId,
            Integer sequence
    ) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exam not found with id: " + examId
                        )
                );

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Question not found with id: " + questionId
                        )
                );

        ExamQuestion examQuestion =
                examQuestionRepository
                        .findByExamAndQuestion(exam, question)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Question is not part of this exam"
                                )
                        );

        if (sequence == null || sequence <= 0) {
            throw new RuntimeException(
                    "Sequence must be greater than zero"
            );
        }

        examQuestion.setSequence(sequence);

        return examQuestionMapper.toDto(examQuestionRepository.save(examQuestion));
    }

    @Override
    @Transactional
    public ExamQuestionDto updateMarks(
            Long examId,
            Long questionId,
            Integer marks
    ) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exam not found with id: " + examId
                        )
                );

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Question not found with id: " + questionId
                        )
                );

        ExamQuestion examQuestion =
                examQuestionRepository
                        .findByExamAndQuestion(exam, question)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Question is not part of this exam"
                                )
                        );

        if (marks == null || marks <= 0) {
            throw new RuntimeException(
                    "Marks must be greater than zero"
            );
        }

        examQuestion.setMarks(marks);

        return examQuestionMapper.toDto(examQuestionRepository.save(examQuestion));
    }
}