package ro.unibuc.hello.dto;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ro.unibuc.hello.models.TeacherEntity;

import java.time.LocalDate;
import java.util.Optional;

public class SubjectGradeTest {

    TeacherEntity teacherEntity = new TeacherEntity("Ionescu", "Alin", "matematica");
    SubjectGradeDto subjectGradeDto = new SubjectGradeDto(teacherEntity, 5, LocalDate.of(2023,3,23));

    @Test
    public void test_teacher (){
        Assertions.assertEquals(teacherEntity, subjectGradeDto.getTeacher());
    }

    @Test
    public void test_grade(){
        Assertions.assertEquals(Optional.ofNullable(5), Optional.ofNullable(subjectGradeDto.getGrade()));
    }

    @Test
    public void test_date(){
        Assertions.assertEquals(LocalDate.of(2023,3,23), subjectGradeDto.getDate());
    }

    @Test
    public void test_subject(){
        Assertions.assertEquals(teacherEntity.getSubject(), subjectGradeDto.getSubject());
    }
}
