package ro.unibuc.hello.service;

import ro.unibuc.hello.dto.StudentDto;
import ro.unibuc.hello.models.StudentEntity;

public interface StudentService {
    StudentEntity addStudent(StudentDto student);


}
