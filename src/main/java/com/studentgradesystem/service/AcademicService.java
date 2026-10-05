package com.studentgradesystem.service;

import com.studentgradesystem.dto.SemesterResultDTO;
import com.studentgradesystem.dto.StudentResultDTO;
import com.studentgradesystem.dto.SubjectResultDTO;
import com.studentgradesystem.exception.StudentNotFoundException;
import com.studentgradesystem.model.Enrollment;
import com.studentgradesystem.model.Student;
import com.studentgradesystem.repository.EnrollmentRepository;
import com.studentgradesystem.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AcademicService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public AcademicService(
            StudentRepository studentRepository,
            EnrollmentRepository enrollmentRepository) {

        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    // =========================================================
    // COMPLETE STUDENT RESULT
    // =========================================================

    public StudentResultDTO getStudentResult(String studentId) {

        Student student = getStudent(studentId);

        List<Enrollment> enrollments =
                new ArrayList<>(
                        enrollmentRepository
                                .findByStudentStudentId(studentId)
                );

        return buildStudentResult(student, enrollments);
    }

    // =========================================================
    // SEMESTER RESULT
    // =========================================================

    public SemesterResultDTO getSemesterResult(
            String studentId,
            int semester) {

        Student student = getStudent(studentId);

        List<Enrollment> enrollments =
                new ArrayList<>(
                        enrollmentRepository
                                .findByStudentStudentIdAndSemester(
                                        studentId,
                                        semester
                                )
                );

        List<SubjectResultDTO> subjectResults =
                new ArrayList<>();

        double totalCreditPoints = 0.0;
        int totalCredits = 0;
        int passedSubjects = 0;
        int failedSubjects = 0;

        for (Enrollment enrollment : enrollments) {

            SubjectResultDTO subjectResult =
                    convertToSubjectResult(enrollment);

            subjectResults.add(subjectResult);

            int credits =
                    enrollment.getSubject().getCredits();

            totalCredits += credits;

            totalCreditPoints +=
                    enrollment.getGradePoint() * credits;

            if (enrollment.isPassed()) {
                passedSubjects++;
            } else {
                failedSubjects++;
            }
        }

        double gpa = calculateGPA(
                totalCreditPoints,
                totalCredits
        );

        String result;

        if (enrollments.isEmpty()) {
            result = "NO RESULT";
        } else if (failedSubjects > 0) {
            result = "FAIL";
        } else {
            result = "PASS";
        }

        return new SemesterResultDTO(
                student.getStudentId(),
                student.getName(),
                semester,
                enrollments.size(),
                passedSubjects,
                failedSubjects,
                totalCredits,
                gpa,
                result,
                subjectResults
        );
    }

    // =========================================================
    // GPA
    // =========================================================

    public double calculateGPA(String studentId) {

        return getStudentResult(studentId).getGpa();
    }

    // =========================================================
    // CGPA
    // =========================================================

    public double calculateCGPA(String studentId) {

        getStudent(studentId);

        List<Enrollment> enrollments =
                new ArrayList<>(
                        enrollmentRepository
                                .findByStudentStudentId(studentId)
                );

        double totalCreditPoints = 0.0;
        int totalCredits = 0;

        for (Enrollment enrollment : enrollments) {

            int credits =
                    enrollment.getSubject().getCredits();

            totalCreditPoints +=
                    enrollment.getGradePoint() * credits;

            totalCredits += credits;
        }

        return calculateGPA(
                totalCreditPoints,
                totalCredits
        );
    }

    // =========================================================
    // PRIVATE HELPERS
    // =========================================================

    private Student getStudent(String studentId) {

        return studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student with ID "
                                        + studentId
                                        + " not found"
                        )
                );
    }

    private StudentResultDTO buildStudentResult(
            Student student,
            List<Enrollment> enrollments) {

        List<SubjectResultDTO> subjectResults =
                new ArrayList<>();

        double totalCreditPoints = 0.0;
        int totalCredits = 0;
        int passedSubjects = 0;
        int failedSubjects = 0;

        for (Enrollment enrollment : enrollments) {

            subjectResults.add(
                    convertToSubjectResult(enrollment)
            );

            int credits =
                    enrollment.getSubject().getCredits();

            totalCredits += credits;

            totalCreditPoints +=
                    enrollment.getGradePoint() * credits;

            if (enrollment.isPassed()) {
                passedSubjects++;
            } else {
                failedSubjects++;
            }
        }

        double gpa = calculateGPA(
                totalCreditPoints,
                totalCredits
        );

        String result;

        if (enrollments.isEmpty()) {
            result = "NO RESULT";
        } else if (failedSubjects > 0) {
            result = "FAIL";
        } else {
            result = "PASS";
        }

        return new StudentResultDTO(
                student.getStudentId(),
                student.getName(),
                enrollments.size(),
                passedSubjects,
                failedSubjects,
                totalCredits,
                gpa,
                result,
                subjectResults
        );
    }

    private SubjectResultDTO convertToSubjectResult(
            Enrollment enrollment) {

        return new SubjectResultDTO(
                enrollment.getSubject().getSubjectCode(),
                enrollment.getSubject().getSubjectName(),
                enrollment.getSubject().getCredits(),
                enrollment.getInternalMarks(),
                enrollment.getExternalMarks(),
                enrollment.getTotalMarks(),
                enrollment.getAttendancePercentage(),
                enrollment.getGrade(),
                enrollment.getGradePoint(),
                enrollment.isPassed()
        );
    }

    private double calculateGPA(
            double totalCreditPoints,
            int totalCredits) {

        if (totalCredits == 0) {
            return 0.0;
        }

        return Math.round(
                (totalCreditPoints / totalCredits) * 100.0
        ) / 100.0;
    }
}