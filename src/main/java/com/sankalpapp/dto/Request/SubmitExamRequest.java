package com.sankalpapp.dto.Request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitExamRequest {

    private List<StudentAnswerRequest> answers;
}