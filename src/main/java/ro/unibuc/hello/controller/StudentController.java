package ro.unibuc.hello.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.ResponseDto;
import ro.unibuc.hello.dto.StudentDto;
import ro.unibuc.hello.dto.SubjectGradeDto;
import ro.unibuc.hello.models.CatalogEntity;
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
    public ResponseEntity<StudentEntity> addStudent(@RequestBody StudentDto dto) {
        return ResponseEntity.ok(studentService.addStudent(dto));
    }

    @PostMapping("/add-grade")
    public ResponseEntity<ResponseDto> addGrade(@RequestBody StudentGradeDto dto) {
        return ResponseEntity.ok(studentService.addGrade(dto));
    }

    @GetMapping("{studentId}/grades")
    public ResponseEntity<List<SubjectGradeDto>> getStudentGrades(@PathVariable String studentId) {
        return ResponseEntity.ok(studentService.getGradesByStudentId(studentId));
    }
}
