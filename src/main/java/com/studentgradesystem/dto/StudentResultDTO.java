package com.studentgradesystem.dto;

import java.util.List;

public class StudentResultDTO {

    private String studentId;
    private String studentName;

    private int totalSubjects;
    private int passedSubjects;
    private int failedSubjects;
    private int totalCredits;

    private double gpa;
    private String result;

    private List<SubjectResultDTO> subjects;

    public StudentResultDTO() {
    }

    public StudentResultDTO(
            String studentId,
            String studentName,
            int totalSubjects,
            int passedSubjects,
            int failedSubjects,
            int totalCredits,
            double gpa,
            String result,
            List<SubjectResultDTO> subjects) {

        this.studentId = studentId;
        this.studentName = studentName;
        this.totalSubjects = totalSubjects;
        this.passedSubjects = passedSubjects;
        this.failedSubjects = failedSubjects;
        this.totalCredits = totalCredits;
        this.gpa = gpa;
        this.result = result;
        this.subjects = subjects;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getTotalSubjects() {
        return totalSubjects;
    }

    public void setTotalSubjects(int totalSubjects) {
        this.totalSubjects = totalSubjects;
    }

    public int getPassedSubjects() {
        return passedSubjects;
    }

    public void setPassedSubjects(int passedSubjects) {
        this.passedSubjects = passedSubjects;
    }

    public int getFailedSubjects() {
        return failedSubjects;
    }

    public void setFailedSubjects(int failedSubjects) {
        this.failedSubjects = failedSubjects;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<SubjectResultDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectResultDTO> subjects) {
        this.subjects = subjects;
    }
}