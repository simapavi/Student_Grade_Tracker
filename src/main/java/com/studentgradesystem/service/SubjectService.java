package com.studentgradesystem.service;

import com.studentgradesystem.exception.DuplicateSubjectException;
import com.studentgradesystem.exception.SubjectNotFoundException;
import com.studentgradesystem.model.Subject;
import com.studentgradesystem.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    // =========================================================
    // CREATE SUBJECT
    // =========================================================

    public Subject addSubject(Subject subject) {

        if (subject == null) {
            throw new IllegalArgumentException(
                    "Subject cannot be null"
            );
        }

        if (subjectRepository.existsById(
                subject.getSubjectCode())) {

            throw new DuplicateSubjectException(
                    "Subject with code "
                            + subject.getSubjectCode()
                            + " already exists"
            );
        }

        return subjectRepository.save(subject);
    }

    // =========================================================
    // GET ALL SUBJECTS
    // =========================================================

    public List<Subject> getAllSubjects() {

        // Explicit ArrayList usage for Java collection requirement
        return new ArrayList<>(
                subjectRepository.findAll()
        );
    }

    // =========================================================
    // GET SUBJECT BY CODE
    // =========================================================

    public Subject getSubjectByCode(String subjectCode) {

        return subjectRepository.findById(subjectCode)
                .orElseThrow(() ->
                        new SubjectNotFoundException(
                                "Subject with code "
                                        + subjectCode
                                        + " not found"
                        )
                );
    }

    // =========================================================
    // UPDATE SUBJECT
    // =========================================================

    public Subject updateSubject(
            String subjectCode,
            Subject updatedSubject) {

        if (updatedSubject == null) {
            throw new IllegalArgumentException(
                    "Subject cannot be null"
            );
        }

        Subject existingSubject =
                getSubjectByCode(subjectCode);

        existingSubject.setSubjectName(
                updatedSubject.getSubjectName()
        );

        existingSubject.setCredits(
                updatedSubject.getCredits()
        );

        return subjectRepository.save(existingSubject);
    }

    // =========================================================
    // DELETE SUBJECT
    // =========================================================

    public boolean deleteSubject(String subjectCode) {

        if (!subjectRepository.existsById(subjectCode)) {

            throw new SubjectNotFoundException(
                    "Subject with code "
                            + subjectCode
                            + " not found"
            );
        }

        subjectRepository.deleteById(subjectCode);

        return true;
    }

    // =========================================================
    // SEARCH SUBJECT BY NAME
    // =========================================================

    public List<Subject> searchByName(String name) {

        if (name == null || name.isBlank()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(
                subjectRepository
                        .findBySubjectNameContainingIgnoreCase(name)
        );
    }

    // =========================================================
    // SORT SUBJECTS BY NAME
    // =========================================================

    public List<Subject> sortByName() {

        List<Subject> subjects =
                new ArrayList<>(
                        subjectRepository.findAll()
                );

        subjects.sort(
                Comparator.comparing(
                        Subject::getSubjectName,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

        return subjects;
    }

    // =========================================================
    // SUBJECT COUNT
    // =========================================================

    public int getSubjectCount() {

        return (int) subjectRepository.count();
    }
}