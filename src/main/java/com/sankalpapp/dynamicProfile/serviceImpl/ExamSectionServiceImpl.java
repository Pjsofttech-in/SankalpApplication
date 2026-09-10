package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.ExamSection;
import com.sankalpapp.dynamicProfile.repository.ExamSectionRepository;
import com.sankalpapp.dynamicProfile.service.ExamSectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExamSectionServiceImpl implements ExamSectionService {

    private static final Long EXAM_SECTION_ID = 1L;

    private final ExamSectionRepository examSectionRepository;

    @Override
    public ExamSection getExamSection() {

        return examSectionRepository
                .findById(EXAM_SECTION_ID)
                .orElse(null);
    }

    @Override
    @Transactional
    public ExamSection updateExamSection(ExamSection examSection) {

        ExamSection existingExamSection =
                examSectionRepository
                        .findById(EXAM_SECTION_ID)
                        .orElse(new ExamSection());

        existingExamSection.setId(EXAM_SECTION_ID);
        existingExamSection.setDescription(
                examSection.getDescription()
        );
        existingExamSection.setExamTitle(examSection.getExamTitle());
        existingExamSection.setExamDate(examSection.getExamDate());
        existingExamSection.setApplicationClosingDate(
                examSection.getApplicationClosingDate()
        );
        existingExamSection.setRegistrationFee(
                examSection.getRegistrationFee()
        );
        existingExamSection.setEligibleClasses(
                examSection.getEligibleClasses()
        );
        existingExamSection.setExamPattern(
                examSection.getExamPattern()
        );
        existingExamSection.setCenters(
                examSection.getCenters()
        );

        return examSectionRepository.save(existingExamSection);
    }
}