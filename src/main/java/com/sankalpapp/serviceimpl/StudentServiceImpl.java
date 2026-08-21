package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.Request.StudentRequest;
import com.sankalpapp.dto.Response.StudentDTO;
import com.sankalpapp.dto.Response.StudentFilterDTO;
import com.sankalpapp.dto.mapper.StudentMapper;
import com.sankalpapp.dto.mapper.StudentSpecification;
import com.sankalpapp.entity.*;
import com.sankalpapp.repository.*;
import com.sankalpapp.service.StudentService;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Strings;
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

        Center center = centerRepository.findById(request.getCenterId())
                .orElseThrow(() -> new RuntimeException("Center not found"));

        Coordinator coordinator = coordinatorRepository.findById(request.getCoordinatorId())
                .orElseThrow(() -> new RuntimeException("Coordinator not found"));

        Payment payment;
        if (StringUtils.isBlank(request.getPaymentMode()) || StringUtils.isBlank(request.getPaymentStatus())) {
            payment = paymentRepository.findByMobileAndPaymentStatusIgnoreCase(request.getMobile(), "success")
                    .orElseThrow(() -> new RuntimeException("Payment not found"));
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

        payment.setStudent(student);
        student = studentRepository.saveAndFlush(student);
        return mapToResponse(student);
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
        student.setSchool(request.getSchoolName());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setActive(request.getActive());

        student.setUser(user);
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
    public StudentDTO getStudentById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id : " + id));

        return mapToResponse(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private StudentDTO mapToResponse(Student student) {

        return StudentDTO.builder()
                .id(student.getId())
                .studentName(student.getStudentName())
                .fatherName(student.getFatherName())
                .lastName(student.getLastName())
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

                .school(student.getSchool())

                .districtId(student.getDistrict().getId())
                .districtName(student.getDistrict().getDistrictName())

                .talukaId(student.getTaluka().getId())
                .talukaName(student.getTaluka().getTalukaName())

                .centerId(student.getCenter().getId())
                .centerName(student.getCenter().getCenterName())

                .coordinatorId(student.getCoordinator().getId())
                .coordinatorName(student.getCoordinator().getFullName())

                .isPaymentDone(student.getPayment() != null)

                .build();
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