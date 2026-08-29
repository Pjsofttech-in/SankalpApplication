package com.sankalpapp.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamRequest {

    private String examName;
    private LocalDate examDate;
    private Integer totalMarks;
    private Integer totalQuestions;
    private Integer duration;

    private LocalDate testStartDate;
    private LocalDate testEndDate;
    private String terms;
    private String image;

    private Boolean downloadTestPaper;
    private Boolean showTestResult;
    private Boolean showAllResult;
    private String allResultPdf;

    private LocalTime startTime;  // 🕒 Stores when the test starts
    private LocalTime endTime;

    private Long categoryId;
}