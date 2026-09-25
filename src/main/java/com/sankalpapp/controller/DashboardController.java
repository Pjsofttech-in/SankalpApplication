package com.sankalpapp.controller;

import com.sankalpapp.dto.response.EbookDashboardResponseDTO;
import com.sankalpapp.dto.response.PaymentDashboardResponseDTO;
import com.sankalpapp.dto.response.StudentDashboardResponseDTO;
import com.sankalpapp.dto.response.TestSeriesDashboardResponseDTO;
import com.sankalpapp.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/students")
    public ResponseEntity<StudentDashboardResponseDTO> getStudentGraph(
            @RequestParam String type,
            @RequestParam(required = false)
            String years,
            @RequestParam(required = false)
            String month) {

        StudentDashboardResponseDTO response =
                dashboardService.getStudentGraph(
                        type,
                        years,
                        month
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/payments")
    public ResponseEntity<PaymentDashboardResponseDTO> getPaymentGraph(
            @RequestParam String type,
            @RequestParam(required = false)
            String years,
            @RequestParam(required = false)
            String month) {

        PaymentDashboardResponseDTO response =
                dashboardService.getPaymentGraph(
                        type,
                        years,
                        month
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/ebook")
    public ResponseEntity<EbookDashboardResponseDTO> getEbookGraph(
            @RequestParam String type,
            @RequestParam(required = false)
            String years,
            @RequestParam(required = false)
            String month) {

        EbookDashboardResponseDTO response =
                dashboardService.getEbookGraph(
                        type,
                        years,
                        month
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/testSeries")
    public ResponseEntity<TestSeriesDashboardResponseDTO> getTestSeriesGraph(
            @RequestParam String type,
            @RequestParam(required = false)
            String years,
            @RequestParam(required = false)
            String month) {

        TestSeriesDashboardResponseDTO response =
                dashboardService.getTestSeriesGraph(
                        type,
                        years,
                        month
                );

        return ResponseEntity.ok(response);
    }
}