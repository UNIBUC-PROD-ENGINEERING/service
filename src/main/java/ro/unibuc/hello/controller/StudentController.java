package ro.unibuc.hello.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.hello.dto.StudentDto;
import ro.unibuc.hello.models.StudentEntity;
import ro.unibuc.hello.service.StudentService;

@RestController
public class StudentController {

    public final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/student")
    public StudentEntity addStudent(@RequestBody StudentDto dto) {
        return studentService.addStudent(dto);
    }
}
