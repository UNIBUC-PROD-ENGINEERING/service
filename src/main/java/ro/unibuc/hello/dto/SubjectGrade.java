package ro.unibuc.hello.dto;
import org.springframework.data.mongodb.core.mapping.DBRef;
import ro.unibuc.hello.models.TeacherEntity;

import java.time.LocalDate;
import java.util.Date;

public class SubjectGrade {

    @DBRef
    public TeacherEntity teacher;
    public String subject;
    public Integer grade;
    public LocalDate date;

    public SubjectGrade(TeacherEntity teacher, Integer grade, LocalDate date) {
        this.teacher = teacher;
        this.subject = this.teacher.subject;
        this.grade = grade;
        this.date = date;
    }
}
