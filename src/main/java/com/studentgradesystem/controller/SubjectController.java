package com.studentgradesystem.controller;

import com.studentgradesystem.model.Subject;
import com.studentgradesystem.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // =========================================================
    // CREATE
    // =========================================================

    @PostMapping
    public ResponseEntity<Subject> addSubject(
            @Valid @RequestBody Subject subject) {

        Subject savedSubject =
                subjectService.addSubject(subject);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedSubject);
    }

    // =========================================================
    // GET ALL
    // =========================================================

    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects() {

        return ResponseEntity.ok(
                subjectService.getAllSubjects()
        );
    }

    // =========================================================
    // GET BY CODE
    // =========================================================

    @GetMapping("/{subjectCode}")
    public ResponseEntity<Subject> getSubjectByCode(
            @PathVariable String subjectCode) {

        return ResponseEntity.ok(
                subjectService.getSubjectByCode(subjectCode)
        );
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @PutMapping("/{subjectCode}")
    public ResponseEntity<Subject> updateSubject(
            @PathVariable String subjectCode,
            @Valid @RequestBody Subject subject) {

        return ResponseEntity.ok(
                subjectService.updateSubject(
                        subjectCode,
                        subject
                )
        );
    }

    // =========================================================
    // DELETE
    // =========================================================

    @DeleteMapping("/{subjectCode}")
    public ResponseEntity<String> deleteSubject(
            @PathVariable String subjectCode) {

        subjectService.deleteSubject(subjectCode);

        return ResponseEntity.ok(
                "Subject with code "
                        + subjectCode
                        + " deleted successfully"
        );
    }

    // =========================================================
    // SEARCH
    // =========================================================

    @GetMapping("/search")
    public ResponseEntity<List<Subject>> searchByName(
            @RequestParam String name) {

        return ResponseEntity.ok(
                subjectService.searchByName(name)
        );
    }

    // =========================================================
    // SORT
    // =========================================================

    @GetMapping("/sort/name")
    public ResponseEntity<List<Subject>> sortByName() {

        return ResponseEntity.ok(
                subjectService.sortByName()
        );
    }

    // =========================================================
    // COUNT
    // =========================================================

    @GetMapping("/count")
    public ResponseEntity<Integer> getSubjectCount() {

        return ResponseEntity.ok(
                subjectService.getSubjectCount()
        );
    }
}