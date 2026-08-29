package com.sankalpapp.dto.response;

import com.sankalpapp.dto.request.StudentAnswerRequest;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitExamRequest {

    private Long studentId;

    private List<StudentAnswerRequest> answers;
}