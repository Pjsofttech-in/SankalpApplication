package com.sankalpapp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerKeyRequest {

    private String title;

    private String link;

    private byte[] pdfBlob;

    private Boolean active;

    private Long examId;
}