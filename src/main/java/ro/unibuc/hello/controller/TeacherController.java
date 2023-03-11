package ro.unibuc.hello.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.hello.dto.TeacherDto;
import ro.unibuc.hello.models.TeacherEntity;
import ro.unibuc.hello.service.TeacherService;

@RestController
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PatchMapping("/{teacherId}/teacher")
    public ResponseEntity<TeacherEntity> updateTeacher(@RequestBody TeacherDto dto,
                                                       @PathVariable String teacherId) {
        return ResponseEntity.ok(teacherService.updateTeacherData(dto, teacherId));
    }
}
