package com.sankalpapp.serviceimpl;

import com.sankalpapp.dto.response.*;
import com.sankalpapp.repository.PaymentRepository;
import com.sankalpapp.repository.StudentRepository;
import com.sankalpapp.repository.TestSeriesOrderRepository;
import com.sankalpapp.repository.VMOrderRepository;
import com.sankalpapp.service.DashboardService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final StudentRepository studentRepository;
    private final PaymentRepository paymentRepository;
    private final VMOrderRepository vmOrderRepository;
    private final TestSeriesOrderRepository testSeriesOrderRepository;

    private static class FilterData {
        int year, year1, year2, month;
    }

    private FilterData getFilterInput(String type, String years, String month){
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Type is required. Use DAY, MONTH or YEAR"
            );
        }

        type = type.toUpperCase();

        if (StringUtils.isBlank(years)) {
            throw new IllegalArgumentException(
                    "Years is required"
            );
        }

        if (StringUtils.isBlank(month)) {
            throw new IllegalArgumentException(
                    "Month is required"
            );
        }

        int year2 = LocalDate.now().getYear();
        int year1 = LocalDate.now().minusYears(1).getYear();
        int year  = LocalDate.now().getYear();
        int month_digit = LocalDate.now().getMonth().getValue();

        if(type.equalsIgnoreCase("DAY")) {
            year = Integer.parseInt(years);
            month_digit = Integer.parseInt(month);
        }

        if(type.equalsIgnoreCase("MONTH")) {
            year = Integer.parseInt(years);
        }

        if(type.equalsIgnoreCase("YEAR")) {
            String[] strArr = years.split("-");
            year1 = Integer.parseInt(strArr[0]);
            year2 = Integer.parseInt(strArr[1]);
        }

        FilterData response = new FilterData();
        response.month = month_digit;
        response.year = year;
        response.year1 = year1;
        response.year2 = year2;
        return response;
    }

    @Override
    public StudentDashboardResponseDTO getStudentGraph(
            String type,
            String years,
            String month) {

        FilterData filterInput = getFilterInput(type, years, month);

        List<Object[]> results = switch (type.toUpperCase()) {
            case "DAY" -> studentRepository.getStudentsDayWise(
                    filterInput.year, filterInput.month
            );
            case "MONTH" -> studentRepository.getStudentsMonthWise(
                    filterInput.year
            );
            case "YEAR" -> studentRepository.getStudentsYearWise(
                    filterInput.year1, filterInput.year2
            );
            default -> throw new IllegalArgumentException(
                    "Invalid type. Use DAY, MONTH or YEAR"
            );
        };

        List<StudentGraphDTO> data = results.stream()
                .map(row -> new StudentGraphDTO(
                        row[0].toString(),
                        ((Number) row[1]).longValue()
                )).sorted(Comparator.comparing(StudentGraphDTO::getPeriod).reversed())
                .toList();

        return new StudentDashboardResponseDTO(
                type,
                data
        );
    }

    @Override
    public PaymentDashboardResponseDTO getPaymentGraph(
            String type,
            String years,
            String month) {

        FilterData filterInput = getFilterInput(type, years, month);

        List<Object[]> results = switch (type.toUpperCase()) {
            case "DAY" -> paymentRepository.getPaymentsDayWise(
                    filterInput.year,
                    filterInput.month
            );
            case "MONTH" -> paymentRepository.getPaymentsMonthWise(
                    filterInput.year
            );
            case "YEAR" -> paymentRepository.getPaymentsYearWise(
                    filterInput.year1, filterInput.year2
            );
            default -> throw new IllegalArgumentException(
                    "Invalid type. Use DAY, MONTH or YEAR"
            );
        };

        List<PaymentGraphDTO> data = results.stream()
                .map(row -> new PaymentGraphDTO(
                        row[0].toString(),
                        ((Number) row[1]).longValue(),
                        ((Number) row[2]).doubleValue()
                ))
                .toList();

        return new PaymentDashboardResponseDTO(
                type,
                data
        );
    }

    @Override
    public EbookDashboardResponseDTO getEbookGraph(
            String type,
            String years,
            String month) {

        FilterData filterInput = getFilterInput(type, years, month);

        List<Object[]> results = switch (type.toUpperCase()) {
            case "DAY" -> vmOrderRepository.getPaymentsDayWise(
                    filterInput.year,
                    filterInput.month
            );
            case "MONTH" -> vmOrderRepository.getPaymentsMonthWise(
                    filterInput.year
            );
            case "YEAR" -> vmOrderRepository.getPaymentsYearWise(
                    filterInput.year1, filterInput.year2
            );
            default -> throw new IllegalArgumentException(
                    "Invalid type. Use DAY, MONTH or YEAR"
            );
        };

        List<EbookGraphDTO> data = results.stream()
                .map(row -> new EbookGraphDTO(
                        row[0].toString(),
                        ((Number) row[1]).longValue(),
                        ((Number) row[2]).doubleValue()
                ))
                .toList();

        return new EbookDashboardResponseDTO(
                type,
                data
        );
    }

    @Override
    public TestSeriesDashboardResponseDTO getTestSeriesGraph(
            String type,
            String years,
            String month) {

        FilterData filterInput = getFilterInput(type, years, month);

        List<Object[]> results = switch (type.toUpperCase()) {
            case "DAY" -> testSeriesOrderRepository.getPaymentsDayWise(
                    filterInput.year,
                    filterInput.month
            );
            case "MONTH" -> testSeriesOrderRepository.getPaymentsMonthWise(
                    filterInput.year
            );
            case "YEAR" -> testSeriesOrderRepository.getPaymentsYearWise(
                    filterInput.year1, filterInput.year2
            );
            default -> throw new IllegalArgumentException(
                    "Invalid type. Use DAY, MONTH or YEAR"
            );
        };

        List<TestSeriesGraphDTO> data = results.stream()
                .map(row -> new TestSeriesGraphDTO(
                        row[0].toString(),
                        ((Number) row[1]).longValue(),
                        ((Number) row[2]).doubleValue()
                ))
                .toList();

        return new TestSeriesDashboardResponseDTO(
                type,
                data
        );
    }
}