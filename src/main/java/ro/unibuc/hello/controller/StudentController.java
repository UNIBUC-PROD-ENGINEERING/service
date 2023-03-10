package ro.unibuc.hello.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.StudentDto;
import ro.unibuc.hello.dto.SubjectGradeDto;
import ro.unibuc.hello.models.StudentEntity;
import ro.unibuc.hello.service.StudentService;

import java.util.List;

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

    @GetMapping("{studentId}/grades")
    public ResponseEntity<List<SubjectGradeDto>> getStudentGrades(@PathVariable String studentId) {
        return ResponseEntity.ok(studentService.getGradesByStudentId(studentId));
    }
}
