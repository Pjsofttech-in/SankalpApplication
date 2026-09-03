package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.mapper.QuestionMapper;
import com.sankalpapp.dto.request.QuestionRequest;
import com.sankalpapp.dto.response.QuestionResponse;
import com.sankalpapp.entity.Question;
import com.sankalpapp.entity.Section;
import com.sankalpapp.repository.QuestionRepository;
import com.sankalpapp.service.QuestionService;
import com.sankalpapp.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private static final String folder = "Question";
    private final SectionService sectionService;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final S3Service s3Service;

    @Override
    @Transactional
    public QuestionResponse create(QuestionRequest questionRequest,
                                   MultipartFile createQuestionFile, MultipartFile optionAFile,
                                   MultipartFile optionBFile,
                                   MultipartFile optionCFile,
                                   MultipartFile optionDFile,
                                   MultipartFile answerSupportingFile) {

        Question question = new Question();
        updateEntityFromRequest(question, questionRequest, createQuestionFile, optionAFile,
                optionBFile, optionCFile, optionDFile, answerSupportingFile);

        return questionMapper.toResponse(questionRepository.save(question));
    }

    private String uploadIfImage(MultipartFile file, String textValue) {
        if (file != null && !file.isEmpty()) {
            try {
                return s3Service.uploadFile(file, folder);
            } catch (IOException e) {
                throw new RuntimeException("Error uploading image to S3: " + e.getMessage());
            }
        }
        return textValue;
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
            QuestionRequest questionRequest,
            MultipartFile createQuestionFile, MultipartFile optionAFile,
            MultipartFile optionBFile,
            MultipartFile optionCFile,
            MultipartFile optionDFile,
            MultipartFile answerSupportingFile
    ) {

        Question question = questionRepository.findById(id).orElseThrow();
        updateEntityFromRequest(question, questionRequest, createQuestionFile, optionAFile,
                optionBFile, optionCFile, optionDFile, answerSupportingFile);
        return questionMapper.toResponse(questionRepository.save(question));
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Question question = questionRepository.findById(id).orElseThrow();
        s3Service.deleteFileByUrl(question.getQuestion());
        s3Service.deleteFileByUrl(question.getOptionA());
        s3Service.deleteFileByUrl(question.getOptionB());
        s3Service.deleteFileByUrl(question.getOptionC());
        s3Service.deleteFileByUrl(question.getOptionD());
        s3Service.deleteFileByUrl(question.getAnswerSupportingFile());

        questionRepository.delete(question);
    }

    /**
     * Updates an existing Question entity with non-null values from the request.
     */
    public void updateEntityFromRequest(Question entity, QuestionRequest request,
                                        MultipartFile createQuestionFile, MultipartFile optionAFile,
                                        MultipartFile optionBFile, MultipartFile optionCFile,
                                        MultipartFile optionDFile,
                                        MultipartFile answerSupportingFile) {
        if (entity == null || request == null) {
            return;
        }

        entity.setQuestion(uploadIfImage(createQuestionFile, request.getQuestion()));
        if (StringUtils.hasText(request.getQuestionType())) {
            entity.setQuestionType(request.getQuestionType());
        }
        entity.setOptionA(uploadIfImage(optionAFile, request.getOptionA()));
        entity.setOptionB(uploadIfImage(optionBFile, request.getOptionB()));
        entity.setOptionC(uploadIfImage(optionCFile, request.getOptionC()));
        entity.setOptionD(uploadIfImage(optionDFile, request.getOptionD()));
        if (StringUtils.hasText(request.getCorrectAnswer())) {
            entity.setCorrectAnswer(request.getCorrectAnswer());
        }
        if (StringUtils.hasText(request.getAnswerExplanation())) {
            entity.setAnswerExplanation(request.getAnswerExplanation());
        }
        entity.setAnswerSupportingFile(uploadIfImage(answerSupportingFile, request.getAnswerSupportingFile()));
        if (request.getActive() != null) {
            entity.setActive(request.getActive());
        }
        if (request.getSectionId() != null) {
            Section section = sectionService.getSectionById(request.getSectionId());
            entity.setSection(section);
        }
    }
}