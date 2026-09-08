package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.dto.MentorDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MentorService {

    MentorDTO createMentor(MentorDTO request, MultipartFile image);

    MentorDTO updateMentor(Long id, MentorDTO request, MultipartFile image);

    MentorDTO getMentorById(Long id);

    List<MentorDTO> getAllMentors();

    List<MentorDTO> getActiveMentors();

    void deleteMentor(Long id);
}