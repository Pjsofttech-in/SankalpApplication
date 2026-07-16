package com.testapplication.serviceimpl;

import com.testapplication.entity.Student;
import com.testapplication.repository.StudentRepository;
import com.testapplication.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {

        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Student Email already exists.");
        }

        if (studentRepository.existsByMobile(student.getMobile())) {
            throw new RuntimeException("Student Mobile already exists.");
        }

        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {

        Student existingStudent = getStudentById(id);

        existingStudent.setStudentName(student.getStudentName());
        existingStudent.setMobile(student.getMobile());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setGender(student.getGender());
        existingStudent.setStudentClass(student.getStudentClass());
        existingStudent.setMedium(student.getMedium());
        existingStudent.setAddress(student.getAddress());
        existingStudent.setVillage(student.getVillage());
        existingStudent.setState(student.getState());
        existingStudent.setPincode(student.getPincode());
        existingStudent.setDateOfBirth(student.getDateOfBirth());
        existingStudent.setActive(student.getActive());

        // Relations
        existingStudent.setUser(student.getUser());
        existingStudent.setSchool(student.getSchool());
        existingStudent.setDistrict(student.getDistrict());
        existingStudent.setTaluka(student.getTaluka());
        existingStudent.setCenter(student.getCenter());
        existingStudent.setCoordinator(student.getCoordinator());

        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudent(Long id) {

        Student student = getStudentById(id);
        studentRepository.delete(student);
    }

    @Override
    public Student getStudentById(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found with id : " + id));
    }

    @Override
    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }
}