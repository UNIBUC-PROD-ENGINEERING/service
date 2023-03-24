package ro.unibuc.hello.dto;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ro.unibuc.hello.models.TeacherEntity;

import java.time.LocalDate;

public class StudentGradeTest {
    TeacherEntity teacher = new TeacherEntity("Ioana", "Ionescu", "matematica");
    SubjectGradeDto subjectGradeDto = new SubjectGradeDto(teacher, 10, LocalDate.of(2000, 12, 06));

    @Test
    public void test_grade() {
        Assertions.assertSame(10, subjectGradeDto.getGrade());
    }
    @Test
    public void test_date() {
        LocalDate expectedDate = LocalDate.of(2000, 12, 06);
        Assertions.assertEquals(expectedDate, subjectGradeDto.getDate());
    }

    @Test
    public void test_teacher() {
        Assertions.assertSame(teacher, subjectGradeDto.getTeacher());
    }

}
