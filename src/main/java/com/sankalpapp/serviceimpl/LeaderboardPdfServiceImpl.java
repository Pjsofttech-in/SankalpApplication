package com.sankalpapp.serviceimpl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sankalpapp.dto.Response.LeaderboardResponse;
import com.sankalpapp.entity.TestSeries;
import com.sankalpapp.repository.TestSeriesRepository;
import com.sankalpapp.service.LeaderboardPdfService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class LeaderboardPdfServiceImpl
        implements LeaderboardPdfService {

    TestSeriesRepository testSeriesRepository;

    @Override
    public byte[] generateExamLeaderboardPdf(
            Long examId,
            List<LeaderboardResponse> leaderboard
    ) {

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            Document document =
                    new Document(PageSize.A4);

            PdfWriter.getInstance(
                    document,
                    outputStream
            );

            document.open();

            /*
             * Title
             */
            Font titleFont =
                    new Font(
                            Font.HELVETICA,
                            20,
                            Font.BOLD
                    );

            Paragraph title =
                    new Paragraph(
                            "Exam Leaderboard",
                            titleFont
                    );

            title.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(title);

            document.add(
                    new Paragraph(
                            "Exam ID: " + examId
                    )
            );

            document.add(
                    new Paragraph(" ")
            );

            /*
             * Table
             *
             * Rank
             * Student
             * Marks
             * Percentage
             * Time
             */
            PdfPTable table =
                    new PdfPTable(6);

            table.setWidthPercentage(100);

            table.setWidths(
                    new float[]{
                            1f,
                            3f,
                            1.5f,
                            1.5f,
                            1.5f,
                            2f
                    }
            );

            addHeader(table, "Rank");
            addHeader(table, "Student");
            addHeader(table, "Marks");
            addHeader(table, "Total");
            addHeader(table, "Percentage");
            addHeader(table, "Time");

            for (LeaderboardResponse response :
                    leaderboard) {

                table.addCell(
                        String.valueOf(
                                response.getRank()
                        )
                );

                table.addCell(
                        response.getStudentName()
                );

                table.addCell(
                        String.valueOf(
                                response.getObtainedMarks()
                        )
                );

                table.addCell(
                        String.valueOf(
                                response.getTotalMarks()
                        )
                );

                table.addCell(
                        String.format(
                                "%.2f%%",
                                response.getPercentage()
                        )
                );

                table.addCell(
                        formatTime(
                                response.getTimeTakenSeconds()
                        )
                );
            }

            document.add(table);

            document.close();

            return outputStream.toByteArray();

        } catch (DocumentException e) {

            throw new RuntimeException(
                    "Failed to generate leaderboard PDF",
                    e
            );
        }
    }

    private void addHeader(
            PdfPTable table,
            String text
    ) {

        PdfPCell cell =
                new PdfPCell(
                        new Phrase(text)
                );

        cell.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        table.addCell(cell);
    }

    private String formatTime(
            Long seconds
    ) {

        if (seconds == null
                || seconds == Long.MAX_VALUE) {

            return "-";
        }

        long hours =
                seconds / 3600;

        long minutes =
                (seconds % 3600) / 60;

        long remainingSeconds =
                seconds % 60;

        if (hours > 0) {

            return String.format(
                    "%02d:%02d:%02d",
                    hours,
                    minutes,
                    remainingSeconds
            );
        }

        return String.format(
                "%02d:%02d",
                minutes,
                remainingSeconds
        );
    }

    @Override
    public byte[] generateTestSeriesLeaderboardPdf(
            Long testSeriesId,
            List<LeaderboardResponse> leaderboard
    ) {

        TestSeries testSeries =
                testSeriesRepository.findById(testSeriesId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Test series not found"
                                )
                        );

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            Document document =
                    new Document(PageSize.A4);

            PdfWriter.getInstance(
                    document,
                    outputStream
            );

            document.open();

            /*
             * Title
             */
            Font titleFont =
                    new Font(
                            Font.HELVETICA,
                            20,
                            Font.BOLD
                    );

            Paragraph title =
                    new Paragraph(
                            "Test Series Leaderboard",
                            titleFont
                    );

            title.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(title);

            /*
             * Test Series Name
             */
            Paragraph seriesName =
                    new Paragraph(
                            "Test Series: "
                                    + testSeries.getTitle()
                    );

            seriesName.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(seriesName);

            document.add(
                    new Paragraph(" ")
            );

            /*
             * Table
             */
            PdfPTable table =
                    new PdfPTable(6);

            table.setWidthPercentage(100);

            table.setWidths(
                    new float[]{
                            1f,
                            3f,
                            1.5f,
                            1.5f,
                            1.5f,
                            2f
                    }
            );

            addHeader(table, "Rank");
            addHeader(table, "Student");
            addHeader(table, "Marks");
            addHeader(table, "Total");
            addHeader(table, "Percentage");
            addHeader(table, "Time");

            for (LeaderboardResponse response :
                    leaderboard) {

                table.addCell(
                        String.valueOf(
                                response.getRank()
                        )
                );

                table.addCell(
                        response.getStudentName()
                );

                table.addCell(
                        String.valueOf(
                                response.getObtainedMarks()
                        )
                );

                table.addCell(
                        String.valueOf(
                                response.getTotalMarks()
                        )
                );

                table.addCell(
                        String.format(
                                "%.2f%%",
                                response.getPercentage()
                        )
                );

                table.addCell(
                        formatTime(
                                response.getTimeTakenSeconds()
                        )
                );
            }

            document.add(table);

            document.close();

            return outputStream.toByteArray();

        } catch (DocumentException e) {

            throw new RuntimeException(
                    "Failed to generate test series leaderboard PDF",
                    e
            );
        }
    }
}