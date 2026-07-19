package com.gradecalculator.model;

import java.util.Map;

public class Student {
    private final String name;
    private final String rollNumber;
    private final Map<String, Double> subjectMarks;

    public Student(String name, String rollNumber, Map<String, Double> subjectMarks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.subjectMarks = subjectMarks;
    }

    public String getName() { return name; }
    public String getRollNumber() { return rollNumber; }
    public Map<String, Double> getSubjectMarks() { return subjectMarks; }

    public double calculateTotal() {
        double sum = 0;
        for (double val : subjectMarks.values()) {
            sum += val;
        }
        return sum;
    }

    public double calculateAverage() {
        if (subjectMarks.isEmpty()) return 0;
        return calculateTotal() / subjectMarks.size();
    }

    /**
     * Determines status: PASS, A.T.K.T., or FAIL based on subject fail counts.
     */
    public String getStatus() {
        int failedSubjectsCount = 0;

        // Count how many subjects have a score below 35
        for (double mark : subjectMarks.values()) {
            if (mark < 35) {
                failedSubjectsCount++;
            }
        }

        // Apply Logic Rules
        if (failedSubjectsCount == 0) {
            return "PASS";
        } else if (failedSubjectsCount <= 2) {
            return "A.T.K.T.";
        } else {
            return "FAIL";
        }
    }

    /**
     * Determines the final letter grade.
     */
    public String getGrade() {
        String status = getStatus();

        if (status.equals("FAIL")) return "F";
        if (status.equals("A.T.K.T.")) return "N/A"; // Grade withheld/Not Applicable for ATKT

        double avg = calculateAverage();
        if (avg >= 90) return "A+";
        if (avg >= 80) return "A";
        if (avg >= 70) return "B";
        if (avg >= 60) return "C";
        if (avg >= 50) return "D";
        if (avg >= 35) return "E";
        return "F";
    }
}