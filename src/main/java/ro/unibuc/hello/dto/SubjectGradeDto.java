package ro.unibuc.hello.dto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import ro.unibuc.hello.models.TeacherEntity;

import java.time.LocalDate;

@Getter
@Setter
public class SubjectGradeDto {

    @DBRef
    private TeacherEntity teacher;
    private String subject;
    private Integer grade;
    private LocalDate date;

    public SubjectGradeDto(TeacherEntity teacher, Integer grade, LocalDate date) {
        this.teacher = teacher;
        this.subject = this.teacher.getSubject();
        this.grade = grade;
        this.date = date;
    }

}
