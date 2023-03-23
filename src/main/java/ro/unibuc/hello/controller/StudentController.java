package ro.unibuc.hello.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.ResponseDto;
import ro.unibuc.hello.dto.StudentDto;
import ro.unibuc.hello.dto.StudentGradeDto;
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
    public ResponseEntity<ResponseDto> addStudent(@RequestBody StudentDto dto) {
        StudentEntity student = studentService.addStudent(dto);
        if (student == null) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, "Invalid student provided"));
        }
        return ResponseEntity.ok(new ResponseDto(true, student.toString()));
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
