package com.studentgradesystem.service;

import com.studentgradesystem.exception.DuplicateEnrollmentException;
import com.studentgradesystem.exception.EnrollmentNotFoundException;
import com.studentgradesystem.exception.StudentNotFoundException;
import com.studentgradesystem.exception.SubjectNotFoundException;
import com.studentgradesystem.model.Enrollment;
import com.studentgradesystem.model.Student;
import com.studentgradesystem.model.Subject;
import com.studentgradesystem.repository.EnrollmentRepository;
import com.studentgradesystem.repository.StudentRepository;
import com.studentgradesystem.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            SubjectRepository subjectRepository) {

        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    // =========================================================
    // CREATE ENROLLMENT
    // =========================================================

    public Enrollment addEnrollment(
            String studentId,
            String subjectCode,
            Enrollment enrollment) {

        if (enrollment == null) {
            throw new IllegalArgumentException(
                    "Enrollment cannot be null"
            );
        }

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new StudentNotFoundException(
                                        "Student with ID "
                                                + studentId
                                                + " not found"
                                )
                        );

        Subject subject =
                subjectRepository.findById(subjectCode)
                        .orElseThrow(() ->
                                new SubjectNotFoundException(
                                        "Subject with code "
                                                + subjectCode
                                                + " not found"
                                )
                        );

        if (enrollmentRepository
                .existsByStudentStudentIdAndSubjectSubjectCode(
                        studentId,
                        subjectCode)) {

            throw new DuplicateEnrollmentException(
                    "Student "
                            + studentId
                            + " is already enrolled in "
                            + subjectCode
            );
        }

        enrollment.setStudent(student);
        enrollment.setSubject(subject);
        enrollment.setSemester(enrollment.getSemester());

        return enrollmentRepository.save(enrollment);
    }

    // =========================================================
    // GET ALL ENROLLMENTS
    // =========================================================

    public List<Enrollment> getAllEnrollments() {

        return new ArrayList<>(
                enrollmentRepository.findAll()
        );
    }

    // =========================================================
    // GET ENROLLMENT BY ID
    // =========================================================

    public Enrollment getEnrollmentById(Long id) {

        return enrollmentRepository.findById(id)
                .orElseThrow(() ->
                        new EnrollmentNotFoundException(
                                "Enrollment with ID "
                                        + id
                                        + " not found"
                        )
                );
    }

    // =========================================================
    // GET STUDENT ENROLLMENTS
    // =========================================================

    public List<Enrollment> getEnrollmentsByStudent(
            String studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(
                    "Student with ID "
                            + studentId
                            + " not found"
            );
        }

        return new ArrayList<>(
                enrollmentRepository
                        .findByStudentStudentId(studentId)
        );
    }

    // =========================================================
    // UPDATE ENROLLMENT
    // =========================================================

    public Enrollment updateEnrollment(
            Long id,
            Enrollment updatedEnrollment) {

        if (updatedEnrollment == null) {
            throw new IllegalArgumentException(
                    "Enrollment cannot be null"
            );
        }

        Enrollment existingEnrollment =
                getEnrollmentById(id);

        existingEnrollment.setInternalMarks(
                updatedEnrollment.getInternalMarks()
        );

        existingEnrollment.setExternalMarks(
                updatedEnrollment.getExternalMarks()
        );

        existingEnrollment.setAttendancePercentage(
                updatedEnrollment.getAttendancePercentage()
        );

        return enrollmentRepository.save(
                existingEnrollment
        );
    }

    // =========================================================
    // DELETE ENROLLMENT
    // =========================================================

    public boolean deleteEnrollment(Long id) {

        if (!enrollmentRepository.existsById(id)) {

            throw new EnrollmentNotFoundException(
                    "Enrollment with ID "
                            + id
                            + " not found"
            );
        }

        enrollmentRepository.deleteById(id);

        return true;
    }
}