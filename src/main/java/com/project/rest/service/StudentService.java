package com.project.rest.service;

import com.project.rest.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Optional<Student> getStudent(int id);
    List<Student> getStudenci();
    void addStudent(Student student);
}
