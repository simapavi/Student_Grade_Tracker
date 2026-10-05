package com.studentgradesystem.repository;

import com.studentgradesystem.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, String> {

    List<Subject> findBySubjectNameContainingIgnoreCase(String subjectName);
}