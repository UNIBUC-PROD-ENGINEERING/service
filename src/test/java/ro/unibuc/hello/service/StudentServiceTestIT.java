package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.Student;
import ro.unibuc.hello.data.StudentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Tag("IT")
class StudentServiceTestIT {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    @Test
    void getAllStudents() {
        List<Student> students = studentRepository.findAll();
        Assertions.assertEquals(students, studentService.getAllStudents());
    }
}