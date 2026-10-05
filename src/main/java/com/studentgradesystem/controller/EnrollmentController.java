package com.studentgradesystem.controller;

import com.studentgradesystem.model.Enrollment;
import com.studentgradesystem.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(
            EnrollmentService enrollmentService) {

        this.enrollmentService = enrollmentService;
    }

    // =========================================================
    // CREATE
    // =========================================================

    @PostMapping("/student/{studentId}/subject/{subjectCode}")
    public ResponseEntity<Enrollment> addEnrollment(
            @PathVariable String studentId,
            @PathVariable String subjectCode,
            @Valid @RequestBody Enrollment enrollment) {

        Enrollment savedEnrollment =
                enrollmentService.addEnrollment(
                        studentId,
                        subjectCode,
                        enrollment
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedEnrollment);
    }

    // =========================================================
    // GET ALL
    // =========================================================

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {

        return ResponseEntity.ok(
                enrollmentService.getAllEnrollments()
        );
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                enrollmentService.getEnrollmentById(id)
        );
    }

    // =========================================================
    // GET BY STUDENT
    // =========================================================

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByStudent(
            @PathVariable String studentId) {

        return ResponseEntity.ok(
                enrollmentService
                        .getEnrollmentsByStudent(studentId)
        );
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> updateEnrollment(
            @PathVariable Long id,
            @Valid @RequestBody Enrollment enrollment) {

        return ResponseEntity.ok(
                enrollmentService.updateEnrollment(
                        id,
                        enrollment
                )
        );
    }

    // =========================================================
    // DELETE
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEnrollment(
            @PathVariable Long id) {

        enrollmentService.deleteEnrollment(id);

        return ResponseEntity.ok(
                "Enrollment with ID "
                        + id
                        + " deleted successfully"
        );
    }
}