package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Request.StudentRequest;
import com.sankalpapp.dto.Response.StudentResponse;
import com.sankalpapp.entity.*;
import com.sankalpapp.repository.*;
import com.sankalpapp.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final DistrictRepository districtRepository;
    private final TalukaRepository talukaRepository;
    private final CenterRepository centerRepository;
    private final CoordinatorRepository coordinatorRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public StudentResponse saveStudent(StudentRequest request) {

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Student Email already exists.");
        }

        if (studentRepository.existsByMobile(request.getMobile())) {
            throw new RuntimeException("Student Mobile already exists.");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new RuntimeException("School not found"));

        District district = districtRepository.findById(request.getDistrictId())
                .orElseThrow(() -> new RuntimeException("District not found"));

        Taluka taluka = talukaRepository.findById(request.getTalukaId())
                .orElseThrow(() -> new RuntimeException("Taluka not found"));

        Center center = centerRepository.findById(request.getCenterId())
                .orElseThrow(() -> new RuntimeException("Center not found"));

        Coordinator coordinator = coordinatorRepository.findById(request.getCoordinatorId())
                .orElseThrow(() -> new RuntimeException("Coordinator not found"));

        Payment payment = paymentRepository.findByMobileAndPaymentStatusIgnoreCase(request.getMobile(), "success")
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Student student = Student.builder()
                .studentName(request.getStudentName())
                .mobile(request.getMobile())
                .email(request.getEmail())
                .gender(request.getGender())
                .studentClass(request.getStudentClass())
                .medium(request.getMedium())
                .address(request.getAddress())
                .village(request.getVillage())
                .state(request.getState())
                .pincode(request.getPincode())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .active(request.getActive())
                .user(user)
                .payment(payment)
                .school(school)
                .district(district)
                .taluka(taluka)
                .center(center)
                .coordinator(coordinator)
                .build();

        payment.setStudent(student);
        student = studentRepository.saveAndFlush(student);
        return mapToResponse(student);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id : " + id));

        User user = userRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new RuntimeException("School not found"));

        District district = districtRepository.findById(request.getDistrictId())
                .orElseThrow(() -> new RuntimeException("District not found"));

        Taluka taluka = talukaRepository.findById(request.getTalukaId())
                .orElseThrow(() -> new RuntimeException("Taluka not found"));

        Center center = centerRepository.findById(request.getCenterId())
                .orElseThrow(() -> new RuntimeException("Center not found"));

        Coordinator coordinator = coordinatorRepository.findById(request.getCoordinatorId())
                .orElseThrow(() -> new RuntimeException("Coordinator not found"));

        student.setStudentName(request.getStudentName());
        student.setMobile(request.getMobile());
        student.setEmail(request.getEmail());
        student.setGender(request.getGender());
        student.setStudentClass(request.getStudentClass());
        student.setMedium(request.getMedium());
        student.setAddress(request.getAddress());
        student.setVillage(request.getVillage());
        student.setState(request.getState());
        student.setPincode(request.getPincode());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setActive(request.getActive());

        student.setUser(user);
        student.setSchool(school);
        student.setDistrict(district);
        student.setTaluka(taluka);
        student.setCenter(center);
        student.setCoordinator(coordinator);

        return mapToResponse(studentRepository.save(student));
    }

    @Override
    public void deleteStudent(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id : " + id));

        studentRepository.delete(student);
    }

    @Override
    public StudentResponse getStudentById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id : " + id));

        return mapToResponse(student);
    }

    @Override
    public List<StudentResponse> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private StudentResponse mapToResponse(Student student) {

        return StudentResponse.builder()
                .id(student.getId())
                .studentName(student.getStudentName())
                .mobile(student.getMobile())
                .email(student.getEmail())
                .gender(student.getGender())
                .studentClass(student.getStudentClass())
                .medium(student.getMedium())
                .address(student.getAddress())
                .village(student.getVillage())
                .state(student.getState())
                .pincode(student.getPincode())
                .dateOfBirth(student.getDateOfBirth())
                .active(student.getActive())

                .schoolId(student.getSchool().getId())
                .schoolName(student.getSchool().getSchoolName())

                .districtId(student.getDistrict().getId())
                .districtName(student.getDistrict().getDistrictName())

                .talukaId(student.getTaluka().getId())
                .talukaName(student.getTaluka().getTalukaName())

                .centerId(student.getCenter().getId())
                .centerName(student.getCenter().getCenterName())

                .coordinatorId(student.getCoordinator().getId())
                .coordinatorName(student.getCoordinator().getFullName())

                .isPaymentDone(student.getPayment()!=null)

                .build();
    }
}