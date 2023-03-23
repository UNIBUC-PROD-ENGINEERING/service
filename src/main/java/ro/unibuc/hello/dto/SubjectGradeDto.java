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


    public TeacherEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
