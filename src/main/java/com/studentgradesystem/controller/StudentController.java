package com.studentgradesystem.controller;

import com.studentgradesystem.model.Enrollment;
import com.studentgradesystem.model.Student;
import com.studentgradesystem.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
    this.studentService = studentService;   
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Student> addStudent(
            @Valid @RequestBody Student student) {

        Student createdStudent = studentService.addStudent(student);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    // READ - all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {

        return ResponseEntity.ok(
                studentService.getAllStudents()
        );
    }

    // READ - student by ID
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(
            @PathVariable String studentId) {

        Student student =
                studentService.getStudentById(studentId);

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student);
    }

    // UPDATE
    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable String studentId,
            @Valid @RequestBody Student updatedStudent) {

        Student student =
                studentService.updateStudent(
                        studentId,
                        updatedStudent
                );

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student);
    }

    // DELETE
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable String studentId) {

        boolean deleted =
                studentService.deleteStudent(studentId);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(
            @RequestParam String name) {

        return ResponseEntity.ok(
                studentService.searchByName(name)
        );
    }

    // FILTER BY DEPARTMENT
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Student>> getByDepartment(
            @PathVariable String department) {

        return ResponseEntity.ok(
                studentService.getStudentsByDepartment(
                        department
                )
        );
    }

    // FILTER BY SEMESTER
    @GetMapping("/semester/{semester}")
    public ResponseEntity<List<Student>> getBySemester(
            @PathVariable int semester) {

        return ResponseEntity.ok(
                studentService.getStudentsBySemester(
                        semester
                )
        );
    }

    // COUNT
    @GetMapping("/count")
    public ResponseEntity<Integer> getStudentCount() {

        return ResponseEntity.ok(
                studentService.getStudentCount()
        );
    }

    // ADD SUBJECT + MARKS TO STUDENT
@PostMapping("/{studentId}/enrollments")
public ResponseEntity<Student> addEnrollment(
        @PathVariable String studentId,
        @RequestBody Enrollment enrollment) {

    Student student =
            studentService.addEnrollment(
                    studentId,
                    enrollment
            );

    if (student == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(student);
}

// GET ALL SUBJECTS/MARKS FOR STUDENT
@GetMapping("/{studentId}/enrollments")
public ResponseEntity<List<Enrollment>> getStudentEnrollments(
        @PathVariable String studentId) {

    List<Enrollment> enrollments =
            studentService.getStudentEnrollments(studentId);

    if (enrollments == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(enrollments);
}

// CALCULATE GPA
@GetMapping("/{studentId}/gpa")
public ResponseEntity<Double> calculateGPA(
        @PathVariable String studentId) {

    double gpa = studentService.calculateGPA(studentId);

    if (gpa < 0) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(gpa);
}
}