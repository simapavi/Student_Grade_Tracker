package com.studentgradesystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_code", nullable = false)
    private Subject subject;

    @DecimalMin(
            value = "0.0",
            message = "Internal marks cannot be negative"
    )
    @DecimalMax(
            value = "40.0",
            message = "Internal marks cannot exceed 40"
    )
    @Column(nullable = false)
    private double internalMarks;

    @DecimalMin(
            value = "0.0",
            message = "External marks cannot be negative"
    )
    @DecimalMax(
            value = "60.0",
            message = "External marks cannot exceed 60"
    )
    @Column(nullable = false)
    private double externalMarks;

    @DecimalMin(
            value = "0.0",
            message = "Attendance cannot be negative"
    )
    @DecimalMax(
            value = "100.0",
            message = "Attendance cannot exceed 100"
    )
    @Column(nullable = false)
    private double attendancePercentage;

    @Min(value = 1, message = "Semester must be at least 1")
    @Max(value = 8, message = "Semester cannot exceed 8")
    @Column(nullable = false)
    private int semester;

    public Enrollment() {
    }

    public Enrollment(
            Student student,
            Subject subject,
            double internalMarks,
            double externalMarks,
            double attendancePercentage,
            int semester) {

        this.student = student;
        this.subject = subject;
        this.internalMarks = internalMarks;
        this.externalMarks = externalMarks;
        this.attendancePercentage = attendancePercentage;
        this.semester = semester;
    }

    // =========================================================
    // GETTERS AND SETTERS
    // =========================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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

    public double getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }
    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    // =========================================================
    // ACADEMIC CALCULATIONS
    // =========================================================

    public double getTotalMarks() {
        return internalMarks + externalMarks;
    }

    public String getGrade() {

        double totalMarks = getTotalMarks();

        if (totalMarks >= 90) {
            return "O";
        } else if (totalMarks >= 80) {
            return "A+";
        } else if (totalMarks >= 70) {
            return "A";
        } else if (totalMarks >= 60) {
            return "B+";
        } else if (totalMarks >= 50) {
            return "B";
        } else if (totalMarks >= 40) {
            return "C";
        } else {
            return "F";
        }
    }

    public double getGradePoint() {

        double totalMarks = getTotalMarks();

        if (totalMarks >= 90) {
            return 10;
        } else if (totalMarks >= 80) {
            return 9;
        } else if (totalMarks >= 70) {
            return 8;
        } else if (totalMarks >= 60) {
            return 7;
        } else if (totalMarks >= 50) {
            return 6;
        } else if (totalMarks >= 40) {
            return 5;
        } else {
            return 0;
        }
    }

    public boolean isPassed() {
        return getTotalMarks() >= 40;
    }

    @Override
    public String toString() {

        return "Enrollment{" +
                "id=" + id +
                ", subject=" + subject +
                ", internalMarks=" + internalMarks +
                ", externalMarks=" + externalMarks +
                ", attendancePercentage=" +
                attendancePercentage +
                '}';
    }
}