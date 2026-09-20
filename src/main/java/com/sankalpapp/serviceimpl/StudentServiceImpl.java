package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.mapper.StudentMapper;
import com.sankalpapp.dto.mapper.StudentSpecification;
import com.sankalpapp.dto.request.StudentRequest;
import com.sankalpapp.dto.response.StudentDTO;
import com.sankalpapp.dto.response.StudentFilterDTO;
import com.sankalpapp.entity.*;
import com.sankalpapp.repository.*;
import com.sankalpapp.service.StudentService;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final DistrictRepository districtRepository;
    private final TalukaRepository talukaRepository;
    private final CenterRepository centerRepository;
    private final CoordinatorRepository coordinatorRepository;
    private final UserRepository userRepository;
    private final RoleRepository rolerepository;
    private final PaymentRepository paymentRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public StudentDTO saveStudent(StudentRequest request) {

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Student Email already exists.");
        }

        if (studentRepository.existsByMobile(request.getMobile())) {
            throw new RuntimeException("Student Mobile already exists.");
        }

        User user = null;
        if (Objects.nonNull(request.getUserId())) {
            user = userRepository.findById(request.getUserId()).orElse(null);
        }

        if (Objects.isNull(user)) {
            user = new User();
            user.setRole(rolerepository.findByRoleNameIgnoreCase("student").orElseThrow(() -> new RuntimeException("Student Role not found")));
            user.setEmail(request.getEmail());
            user.setActive(true);
            user.setFullName(request.getStudentName() + " " + request.getFatherName() + " " + request.getLastName());
            user.setMobile(request.getMobile());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.saveAndFlush(user);
        }

        if (StringUtils.isBlank(request.getSchoolName())) {
            throw new RuntimeException("School name is required");
        }

        District district = districtRepository.findById(request.getDistrictId())
                .orElseThrow(() -> new RuntimeException("District not found"));

        Taluka taluka = talukaRepository.findById(request.getTalukaId())
                .orElseThrow(() -> new RuntimeException("Taluka not found"));

        Center center = null;
        if(request.getCenterId()!=null) {
            center = centerRepository.findById(request.getCenterId()).orElse(null);
        }

        Coordinator coordinator = null;
        if(request.getCenterId()!=null) {
            coordinator = coordinatorRepository.findById(request.getCoordinatorId()).orElse(null);
        }

        if (StringUtils.isNotBlank(request.getExamMode()) && "offline".equalsIgnoreCase(request.getExamMode())) {
            if (center == null) {
                throw new RuntimeException("Center not found");
            } else if (coordinator == null) {
                throw new RuntimeException("Coordinator not found");
            }
        } else {
            center = null;
            coordinator = null;
        }

        Payment payment = null;
        if (StringUtils.isBlank(request.getPaymentMode()) || StringUtils.isBlank(request.getPaymentStatus())) {
//            payment = paymentRepository.findByMobileAndPaymentStatusIgnoreCase(request.getMobile(), "success")
//                    .orElseThrow(() -> new RuntimeException("Payment not found"));
        } else {
            String orderId = "order-" + createOfflinePayment();
            String paymentId = "payment-" + createOfflinePayment();
            String transactionId = "transaction-" + createOfflinePayment();
            payment = Payment.builder()
                    .active(true)
                    .amount(request.getAmount())
                    .mobile(request.getMobile())
                    .orderId(orderId)
                    .paymentId(paymentId)
                    .paymentMode(request.getPaymentMode())
                    .paymentStatus(String.valueOf(Payment.PaymentStatus.valueOf(request.getPaymentStatus())))
                    .paymentDate(LocalDateTime.now())
                    .transactionId(transactionId)
                    .build();

            payment = paymentRepository.saveAndFlush(payment);
        }

        Student student = Student.builder()
                .studentName(request.getStudentName())
                .fatherName(request.getFatherName())
                .lastName(request.getLastName())
                .mobile(request.getMobile())
                .email(request.getEmail())
                .examMode(request.getExamMode())
                .gender(request.getGender())
                .studentClass(request.getStudentClass())
                .medium(request.getMedium())
                .address(request.getAddress())
                .village(request.getVillage())
                .state(request.getState())
                .pincode(request.getPincode())
                .school(request.getSchoolName())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .active(request.getActive())
                .user(user)
                .payment(payment)
                .district(district)
                .taluka(taluka)
                .center(center)
                .coordinator(coordinator)
                .build();

        if (payment != null) {
            payment.setStudent(student);
        }
        student = studentRepository.saveAndFlush(student);
        return StudentMapper.toDTO(student);
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentRequest request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id : " + id));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        District district = districtRepository.findById(request.getDistrictId())
                .orElseThrow(() -> new RuntimeException("District not found"));

        Taluka taluka = talukaRepository.findById(request.getTalukaId())
                .orElseThrow(() -> new RuntimeException("Taluka not found"));

        Center center = centerRepository.findById(request.getCenterId()).orElse(null);
//                .orElseThrow(() -> new RuntimeException("Center not found"));

        Coordinator coordinator = coordinatorRepository.findById(request.getCoordinatorId()).orElse(null);
//                .orElseThrow(() -> new RuntimeException("Coordinator not found"));

        if (StringUtils.isNotBlank(request.getExamMode()) && "offline".equalsIgnoreCase(request.getExamMode())) {
            if (center == null) {
                throw new RuntimeException("Center not found");
            } else if (coordinator == null) {
                throw new RuntimeException("Coordinator not found");
            }
        } else {
            center = null;
            coordinator = null;
        }

        student.setStudentName(request.getStudentName());
        student.setMobile(request.getMobile());
        student.setEmail(request.getEmail());
        student.setExamMode(request.getExamMode());
        student.setGender(request.getGender());
        student.setStudentClass(request.getStudentClass());
        student.setMedium(request.getMedium());
        student.setAddress(request.getAddress());
        student.setVillage(request.getVillage());
        student.setState(request.getState());
        student.setPincode(request.getPincode());
        student.setSchool(request.getSchoolName());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setActive(request.getActive());

        student.setUser(user);
        student.setDistrict(district);
        student.setTaluka(taluka);
        student.setCenter(center);
        student.setCoordinator(coordinator);

        return StudentMapper.toDTO(studentRepository.save(student));
    }

    @Override
    public void deleteStudent(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id : " + id));

        studentRepository.delete(student);
    }

    @Override
    public StudentDTO getStudentById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id : " + id));

        return StudentMapper.toDTO(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<StudentDTO> getStudents(
            StudentFilterDTO filter,
            Pageable pageable
    ) {

        Specification<Student> specification =
                StudentSpecification.filter(
                        entityManager,

                        filter.getDistrictId(),
                        filter.getTalukaId(),
                        filter.getCenterId(),

                        filter.getSchool(),
                        filter.getStudentClass(),
                        filter.getMedium(),
                        filter.getGender(),

                        filter.getActive(),

                        filter.getSearch()
                );

        Page<Student> students =
                studentRepository.findAll(
                        specification,
                        pageable
                );

        return students.map(
                StudentMapper::toDTO
        );
    }

    public String createOfflinePayment() {
        // Generates a 40-character unique ID
        String rawUuid = UUID.randomUUID().toString().replace("-", "");
        return "offline-" + rawUuid;
    }
}