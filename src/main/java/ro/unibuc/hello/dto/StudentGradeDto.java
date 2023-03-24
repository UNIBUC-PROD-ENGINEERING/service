package ro.unibuc.hello.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentGradeDto {
    private String studentId;
    private SubjectGradeDto grade;

    public StudentGradeDto(String studentId, SubjectGradeDto grade) {
        this.studentId = studentId;
        this.grade = grade;
    }
}
