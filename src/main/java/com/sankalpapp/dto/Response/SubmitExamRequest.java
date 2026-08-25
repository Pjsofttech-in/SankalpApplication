package com.sankalpapp.dto.Response;

import com.sankalpapp.dto.Request.StudentAnswerRequest;
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