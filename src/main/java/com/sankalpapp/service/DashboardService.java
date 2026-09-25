package com.sankalpapp.service;

import com.sankalpapp.dto.response.EbookDashboardResponseDTO;
import com.sankalpapp.dto.response.PaymentDashboardResponseDTO;
import com.sankalpapp.dto.response.StudentDashboardResponseDTO;
import com.sankalpapp.dto.response.TestSeriesDashboardResponseDTO;

import java.time.LocalDate;

public interface DashboardService {

    StudentDashboardResponseDTO getStudentGraph(
            String type,
            String years,
            String month
    );

    PaymentDashboardResponseDTO getPaymentGraph(
            String type,
            String years,
            String month
    );

    EbookDashboardResponseDTO getEbookGraph(
            String type,
            String years,
            String month);

    TestSeriesDashboardResponseDTO getTestSeriesGraph(
            String type,
            String years,
            String month);
}