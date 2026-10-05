package com.studentgradesystem.controller;

import com.studentgradesystem.dto.SemesterResultDTO;
import com.studentgradesystem.dto.StudentResultDTO;
import com.studentgradesystem.service.AcademicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/academic")
public class AcademicController {

    private final AcademicService academicService;

    public AcademicController(
            AcademicService academicService) {

        this.academicService = academicService;
    }

    // =========================================================
    // COMPLETE RESULT
    // =========================================================

    @GetMapping("/student/{studentId}/result")
    public ResponseEntity<StudentResultDTO> getStudentResult(
            @PathVariable String studentId) {

        return ResponseEntity.ok(
                academicService.getStudentResult(studentId)
        );
    }

    // =========================================================
    // GPA
    // =========================================================

    @GetMapping("/student/{studentId}/gpa")
    public ResponseEntity<Double> getGPA(
            @PathVariable String studentId) {

        return ResponseEntity.ok(
                academicService.calculateGPA(studentId)
        );
    }

    // =========================================================
    // CGPA
    // =========================================================

    @GetMapping("/student/{studentId}/cgpa")
    public ResponseEntity<Double> getCGPA(
            @PathVariable String studentId) {

        return ResponseEntity.ok(
                academicService.calculateCGPA(studentId)
        );
    }

    // =========================================================
    // SEMESTER RESULT
    // =========================================================

    @GetMapping("/student/{studentId}/semester/{semester}")
    public ResponseEntity<SemesterResultDTO> getSemesterResult(
            @PathVariable String studentId,
            @PathVariable int semester) {

        return ResponseEntity.ok(
                academicService.getSemesterResult(
                        studentId,
                        semester
                )
        );
    }
}