package ro.unibuc.hello.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.ResponseDto;
import ro.unibuc.hello.dto.StudentDto;
import ro.unibuc.hello.dto.StudentGradeDto;
import ro.unibuc.hello.dto.SubjectGradeDto;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.models.CatalogEntity;
import ro.unibuc.hello.models.StudentEntity;
import ro.unibuc.hello.service.StudentService;

import java.util.List;

@RestController
public class StudentController {

    public final StudentService studentService;

    @Autowired
    MeterRegistry metricsRegistry;

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
        try {
            return ResponseEntity.ok(studentService.addGrade(dto));
        } catch (EntityNotFoundException exception){
            return ResponseEntity.badRequest().body(new ResponseDto(false, exception.getMessage()));
        }
    }

    @GetMapping("{studentId}/grades")
    @Timed(value = "hello.getstudentgrades.time", description = "Time taken to get grades from student")
    @Counted(value = "hello.getstudentgrades.count", description = "Times getStudentGrades was returned")
    public ResponseEntity<List<SubjectGradeDto>> getStudentGrades(@PathVariable String studentId) {
        return ResponseEntity.ok(studentService.getGradesByStudentId(studentId));
    }
}
