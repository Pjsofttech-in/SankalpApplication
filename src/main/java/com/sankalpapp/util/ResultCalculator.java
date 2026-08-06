package com.sankalpapp.util;

public class ResultCalculator {

    private ResultCalculator() {
    }

    public static double calculatePercentage(int obtainedMarks,
                                             int totalMarks) {

        if (totalMarks == 0) {
            return 0;
        }

        return ((double) obtainedMarks / totalMarks) * 100;
    }

    public static String calculateGrade(double percentage) {

        if (percentage >= 90)
            return "A+";

        if (percentage >= 80)
            return "A";

        if (percentage >= 70)
            return "B+";

        if (percentage >= 60)
            return "B";

        if (percentage >= 50)
            return "C";

        if (percentage >= 35)
            return "D";

        return "F";
    }

    public static String getResultStatus(double percentage) {

        return percentage >= 35 ? "PASS" : "FAIL";
    }
}