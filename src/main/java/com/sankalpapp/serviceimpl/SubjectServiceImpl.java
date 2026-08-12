package com.sankalpapp.serviceimpl;

import com.sankalpapp.entity.Subject;
import com.sankalpapp.entity.TestSeries;
import com.sankalpapp.repository.SubjectRepository;
import com.sankalpapp.repository.TestSeriesRepository;
import com.sankalpapp.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final TestSeriesRepository testSeriesRepository;

    // ✅ Create
    @Override
    public Subject createSubject(Subject subject) {

        Long testSeriesId = subject.getTestSeries().getId();

        TestSeries testSeries = testSeriesRepository.findById(testSeriesId)
                .orElseThrow(() -> new RuntimeException("TestSeries not found with id: " + testSeriesId));

        subject.setTestSeries(testSeries);

        return subjectRepository.save(subject);
    }

    // ✅ Update
    @Override
    public Subject updateSubject(Long id, Subject subject) {

        Subject existing = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));

        existing.setName(subject.getName());
        existing.setTotalMarks(subject.getTotalMarks());
        existing.setTotalQuestions(subject.getTotalQuestions());
        existing.setActive(subject.isActive());

        // Update TestSeries if changed
        Long testSeriesId = subject.getTestSeries().getId();

        TestSeries testSeries = testSeriesRepository.findById(testSeriesId)
                .orElseThrow(() -> new RuntimeException("TestSeries not found with id: " + testSeriesId));

        existing.setTestSeries(testSeries);

        return subjectRepository.save(existing);
    }

    // ✅ Get All
    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    // ✅ Get By id
    @Override
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
    }

    // ✅ Delete
    @Override
    public void deleteSubject(Long id) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));

        subjectRepository.delete(subject);
    }
}
