package ro.unibuc.hello.dto;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ro.unibuc.hello.models.TeacherEntity;

import java.time.LocalDate;

public class StudentGradeTest {
    TeacherEntity teacher = new TeacherEntity("Ioana", "Ionescu", "matematica");
    SubjectGradeDto subjectGradeDto = new SubjectGradeDto(teacher, 10, LocalDate.of(2000, 12, 06));
    StudentGradeDto studentGradeDto = new StudentGradeDto("12", subjectGradeDto);

    @Test
    public void test_studentId() {
        Assertions.assertEquals("12", studentGradeDto.getStudentId());
    }

    @Test
    public void test_subject_grade() {
        Assertions.assertEquals(subjectGradeDto, studentGradeDto.getGrade());
    }
}
