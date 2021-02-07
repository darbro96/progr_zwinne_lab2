package com.project.rest.service;

import com.project.rest.model.Student;
import com.project.rest.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private UserServiceImpl userService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, UserServiceImpl userService) {
        this.studentRepository = studentRepository;
        this.userService=userService;
    }

    @Override
    public Optional<Student> getStudent(int id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> getStudenci()
    {
        return studentRepository.findAll();
    }

    @Override
    public void addStudent(Student student) {
        studentRepository.save(student);
        userService.createNewUser(student);
    }
}
