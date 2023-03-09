package ro.unibuc.hello.service;

import ro.unibuc.hello.dto.StudentDto;
import ro.unibuc.hello.models.StudentEntity;
import ro.unibuc.hello.dto.SubjectGrade;
import java.util.List;


public interface StudentService {
    List<SubjectGrade> getGradesByStudentId(String studentId);
    StudentEntity addStudent(StudentDto student);
}
