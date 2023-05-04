package ro.unibuc.hello.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    MeterRegistry metricsRegistry;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PatchMapping("/{teacherId}/teacher")
    @Timed(value = "hello.updateteacher.time", description = "Time taken to update a teacher")
    @Counted(value = "hello.updateteacher.count", description = "Times updateTeacher was returned")
    public ResponseEntity<TeacherEntity> updateTeacher(@RequestBody TeacherDto dto,
                                                       @PathVariable String teacherId) {
        return ResponseEntity.ok(teacherService.updateTeacherData(dto, teacherId));
    }
}
