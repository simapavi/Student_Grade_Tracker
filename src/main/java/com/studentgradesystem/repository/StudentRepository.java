package com.studentgradesystem.repository;

import com.studentgradesystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository
        extends JpaRepository<Student, String> {

    List<Student> findByDepartmentIgnoreCase(String department);

    List<Student> findBySemester(int semester);

    List<Student> findByNameContainingIgnoreCase(String name);
}