package ro.unibuc.hello.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentGradeDto {
    private String studentId;
    private SubjectGrade grade;


}
