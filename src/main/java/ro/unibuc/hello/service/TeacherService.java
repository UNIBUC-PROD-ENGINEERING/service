package ro.unibuc.hello.service;

import ro.unibuc.hello.dto.TeacherDto;
import ro.unibuc.hello.models.TeacherEntity;

public interface TeacherService {
    TeacherEntity updateTeacherData(TeacherDto dto, String teacherId);
}
