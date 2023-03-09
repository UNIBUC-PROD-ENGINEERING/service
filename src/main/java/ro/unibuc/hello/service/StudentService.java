package ro.unibuc.hello.service;


import ro.unibuc.hello.dto.SubjectGrade;

import java.util.List;

public interface StudentService {
    List<SubjectGrade> getGradesByStudentId(String studentId);
}
