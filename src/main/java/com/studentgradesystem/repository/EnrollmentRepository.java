package com.studentgradesystem.repository;

import com.studentgradesystem.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository
        extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudentStudentId(String studentId);

    List<Enrollment>findByStudentStudentIdAndSemester(String studentId, int semester);

    boolean existsByStudentStudentIdAndSubjectSubjectCode(
            String studentId,
            String subjectCode
    );
}