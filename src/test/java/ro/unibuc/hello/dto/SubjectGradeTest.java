package ro.unibuc.hello.dto;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ro.unibuc.hello.models.TeacherEntity;

import java.time.LocalDate;

public class SubjectGradeTest {

    TeacherEntity teacherEntity = new TeacherEntity();
    SubjectGradeDto subjectGradeDto = new SubjectGradeDto(teacherEntity, 5, LocalDate.of(2023,03,23));

    @Test
    public void test_teacher (){
        Assertions.assertSame(teacherEntity, subjectGradeDto.getTeacher());
    }

    @Test
    public void test_grade(){
        Assertions.assertSame(5, subjectGradeDto.getGrade());
    }
}
