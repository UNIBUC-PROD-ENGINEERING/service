package ro.unibuc.hello.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.StudentRepository;
import ro.unibuc.hello.data.Student;
import ro.unibuc.hello.dto.StudentDTO;
import ro.unibuc.hello.controller.StudentController;

@SpringBootTest
@Tag("IT")
public class StudentControllerIT {
    @Autowired
    StudentController studentController;

    @Test
    void getStudent() {
        Student student = studentController.getStudent("642d31d5a249a13d08983f55");
        Assertions.assertEquals(student.getId(), "642d31d5a249a13d08983f55");
        Assertions.assertEquals(student.getName(), "Ion Popescu");
        Assertions.assertEquals(student.getEmail(), "ion.popescu@gmail.come");
        Assertions.assertEquals(student.getAge(), 22);
    }
}