package com.testapplication.dto.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerKeyResponse {

    private Long id;

    private String title;

    private String link;

    private Boolean active;

    private Long examId;

    private String examName;
}