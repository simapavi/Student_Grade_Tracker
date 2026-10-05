package com.studentgradesystem.service;

import com.studentgradesystem.exception.DuplicateStudentException;
import com.studentgradesystem.exception.StudentNotFoundException;
import com.studentgradesystem.model.Enrollment;
import com.studentgradesystem.model.Student;
import com.studentgradesystem.model.Subject;
import com.studentgradesystem.repository.EnrollmentRepository;
import com.studentgradesystem.repository.StudentRepository;
import com.studentgradesystem.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentService(
            StudentRepository studentRepository,
            SubjectRepository subjectRepository,
            EnrollmentRepository enrollmentRepository) {

        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    // =========================================================
    // STUDENT OPERATIONS
    // =========================================================

    public Student addStudent(Student student) {

        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (student.getStudentId() == null
                || student.getStudentId().isBlank()) {

            throw new IllegalArgumentException(
                    "Student ID cannot be empty"
            );
        }

        if (studentRepository.existsById(student.getStudentId())) {

            throw new DuplicateStudentException(
                    "Student with ID "
                            + student.getStudentId()
                            + " already exists"
            );
        }

        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {

        // Explicit ArrayList usage for Java collection requirement
        return new ArrayList<>(
                studentRepository.findAll()
        );
    }

    public Student getStudentById(String studentId) {

        return studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student with ID "
                                        + studentId
                                        + " not found"
                        )
                );
    }

    public boolean studentExists(String studentId) {

        return studentRepository.existsById(studentId);
    }

    public Student updateStudent(
            String studentId,
            Student updatedStudent) {

        Student existingStudent =
                getStudentById(studentId);

        existingStudent.setName(
                updatedStudent.getName()
        );

        existingStudent.setEmail(
                updatedStudent.getEmail()
        );

        existingStudent.setDepartment(
                updatedStudent.getDepartment()
        );

        existingStudent.setSemester(
                updatedStudent.getSemester()
        );

        return studentRepository.save(existingStudent);
    }

    public boolean deleteStudent(String studentId) {

        if (!studentRepository.existsById(studentId)) {

            throw new StudentNotFoundException(
                    "Student with ID "
                            + studentId
                            + " not found"
            );
        }

        studentRepository.deleteById(studentId);

        return true;
    }

    // =========================================================
    // SEARCH & FILTER
    // =========================================================

    public List<Student> searchByName(String name) {

        if (name == null || name.isBlank()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(
                studentRepository
                        .findByNameContainingIgnoreCase(name)
        );
    }

    public List<Student> getStudentsByDepartment(
            String department) {

        return new ArrayList<>(
                studentRepository
                        .findByDepartmentIgnoreCase(department)
        );
    }

    public List<Student> getStudentsBySemester(
            int semester) {

        return new ArrayList<>(
                studentRepository
                        .findBySemester(semester)
        );
    }

    // =========================================================
    // SORTING USING ARRAYLIST
    // =========================================================

    public List<Student> sortByName() {

        List<Student> students =
                new ArrayList<>(
                        studentRepository.findAll()
                );

        students.sort(
                Comparator.comparing(
                        Student::getName,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

        return students;
    }

    // =========================================================
    // STUDENT COUNT
    // =========================================================

    public int getStudentCount() {

        return (int) studentRepository.count();
    }

    // =========================================================
    // ENROLLMENT OPERATIONS
    // =========================================================

    public Student addEnrollment(
            String studentId,
            Enrollment enrollment) {

        Student student =
                getStudentById(studentId);

        if (enrollment == null
                || enrollment.getSubject() == null) {

            throw new IllegalArgumentException(
                    "Enrollment and subject cannot be null"
            );
        }

        Subject incomingSubject =
                enrollment.getSubject();

        /*
         * Find the subject in the database.
         * If it does not exist, create it.
         */
        Subject subject =
                subjectRepository
                        .findById(
                                incomingSubject.getSubjectCode()
                        )
                        .orElseGet(() ->
                                subjectRepository.save(
                                        incomingSubject
                                )
                        );

        enrollment.setSubject(subject);
        enrollment.setStudent(student);

        enrollmentRepository.save(enrollment);

        return student;
    }

    public List<Enrollment> getStudentEnrollments(
            String studentId) {

        // Throws StudentNotFoundException if student doesn't exist
        getStudentById(studentId);

        return new ArrayList<>(
                enrollmentRepository
                        .findByStudentStudentId(studentId)
        );
    }

    // =========================================================
    // GPA CALCULATION
    // =========================================================

    public double calculateGPA(String studentId) {

        // Verify that the student exists
        getStudentById(studentId);

        List<Enrollment> enrollments =
                getStudentEnrollments(studentId);

        if (enrollments.isEmpty()) {
            return 0.0;
        }

        double totalCreditPoints = 0.0;
        int totalCredits = 0;

        for (Enrollment enrollment : enrollments) {

            int credits =
                    enrollment
                            .getSubject()
                            .getCredits();

            double gradePoint =
                    enrollment.getGradePoint();

            totalCreditPoints +=
                    gradePoint * credits;

            totalCredits += credits;
        }

        if (totalCredits == 0) {
            return 0.0;
        }

        return Math.round(
                (totalCreditPoints / totalCredits) * 100.0
        ) / 100.0;
    }
}