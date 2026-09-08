package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.dto.MentorDTO;
import com.sankalpapp.dynamicProfile.entity.Mentor;
import com.sankalpapp.dynamicProfile.repository.MentorRepository;
import com.sankalpapp.dynamicProfile.service.MentorService;
import com.sankalpapp.serviceimpl.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;
    private final S3Service s3Service;
    private static final String folder = "Mentor";

    @Override
    public MentorDTO createMentor(MentorDTO request, MultipartFile image) {

        Mentor mentor = Mentor.builder()
                .mentorName(request.getMentorName())
                .position(request.getPosition())
                .description(request.getDescription())
                .image(request.getImage())
                .active(true)
                .build();

        uploadFile(image, mentor);

        Mentor savedMentor = mentorRepository.save(mentor);

        return mapToResponse(savedMentor);
    }

    private void uploadFile(MultipartFile pdf, Mentor obj) {
        if (pdf != null) {
            try {
                String fileURL = s3Service.uploadFile(pdf, folder);
                obj.setImage(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
    }

    @Override
    public MentorDTO updateMentor(Long id, MentorDTO request, MultipartFile image) {

        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Mentor not found with id: " + id)
                );

        mentor.setMentorName(request.getMentorName());
        mentor.setPosition(request.getPosition());
        mentor.setDescription(request.getDescription());

        uploadFile(image, mentor);

        Mentor updatedMentor = mentorRepository.save(mentor);

        return mapToResponse(updatedMentor);
    }

    @Override
    public MentorDTO getMentorById(Long id) {

        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Mentor not found with id: " + id)
                );

        return mapToResponse(mentor);
    }

    @Override
    public List<MentorDTO> getAllMentors() {

        return mentorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<MentorDTO> getActiveMentors() {

        return mentorRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteMentor(Long id) {

        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Mentor not found with id: " + id)
                );

        mentor.setActive(false);

        s3Service.deleteFileByUrl(mentor.getImage());

        mentorRepository.save(mentor);
    }

    private MentorDTO mapToResponse(Mentor mentor) {

        return MentorDTO.builder()
                .id(mentor.getId())
                .mentorName(mentor.getMentorName())
                .position(mentor.getPosition())
                .description(mentor.getDescription())
                .image(mentor.getImage())
                .active(mentor.getActive())
                .build();
    }
}