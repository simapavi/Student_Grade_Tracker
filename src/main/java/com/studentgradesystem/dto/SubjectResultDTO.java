package com.studentgradesystem.dto;

public class SubjectResultDTO {

    private String subjectCode;
    private String subjectName;
    private int credits;

    private double internalMarks;
    private double externalMarks;
    private double totalMarks;

    private double attendancePercentage;

    private String grade;
    private double gradePoint;
    private boolean passed;

    public SubjectResultDTO() {
    }

    public SubjectResultDTO(
            String subjectCode,
            String subjectName,
            int credits,
            double internalMarks,
            double externalMarks,
            double totalMarks,
            double attendancePercentage,
            String grade,
            double gradePoint,
            boolean passed) {

        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credits = credits;
        this.internalMarks = internalMarks;
        this.externalMarks = externalMarks;
        this.totalMarks = totalMarks;
        this.attendancePercentage = attendancePercentage;
        this.grade = grade;
        this.gradePoint = gradePoint;
        this.passed = passed;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public double getInternalMarks() {
        return internalMarks;
    }

    public void setInternalMarks(double internalMarks) {
        this.internalMarks = internalMarks;
    }

    public double getExternalMarks() {
        return externalMarks;
    }

    public void setExternalMarks(double externalMarks) {
        this.externalMarks = externalMarks;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(double gradePoint) {
        this.gradePoint = gradePoint;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}