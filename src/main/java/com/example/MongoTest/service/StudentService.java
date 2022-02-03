package com.example.MongoTest.service;

import com.example.MongoTest.models.Student;
import com.example.MongoTest.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    @Autowired
    private final StudentRepository studentRepository;

    @Transactional
    public void updateStudentFirstName(String oldName, String newName){
        List<Student> student = studentRepository.findStudentsByFirstName(oldName);
        student.forEach(s -> s.setFirstName(newName));
        studentRepository.saveAll(student);
    }
}
