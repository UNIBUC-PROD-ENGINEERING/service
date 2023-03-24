package ro.unibuc.hello.controller;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.shaded.okhttp3.Response;
import ro.unibuc.hello.dto.ResponseDto;
import ro.unibuc.hello.dto.StudentDto;
import ro.unibuc.hello.dto.StudentGradeDto;
import ro.unibuc.hello.dto.SubjectGradeDto;
import ro.unibuc.hello.models.StudentEntity;
import ro.unibuc.hello.models.TeacherEntity;
import ro.unibuc.hello.repositories.CatalogRepository;
import ro.unibuc.hello.repositories.StudentRepository;
import ro.unibuc.hello.service.StudentServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentControllerTest {

    @InjectMocks
    StudentController studentController;
    @Mock
    StudentServiceImpl studentService;


    @Test
    public void checkAddValidStudent() {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("test");
        studentDto.setLastName("test");
        studentDto.setClassName("testA");
        studentDto.setBirthDay(LocalDate.now());

        StudentEntity newStudent = new StudentEntity(studentDto.getFirstName(), studentDto.getLastName(), studentDto.getClassName(), studentDto.getBirthDay());
        newStudent.setId(new ObjectId().toString());

        when(studentService.addStudent(any(StudentDto.class))).thenReturn(newStudent);

        ResponseEntity<ResponseDto> expected = ResponseEntity.ok(new ResponseDto(true, newStudent.toString()));
        ResponseEntity<ResponseDto> actual = studentController.addStudent(studentDto);

        Assertions.assertNotNull(expected.getBody());
        Assertions.assertNotNull(actual.getBody());
        Assertions.assertEquals(expected.getStatusCode(), actual.getStatusCode());
        Assertions.assertEquals(expected.getBody().getMessage(), actual.getBody().getMessage());
    }

    @Test
    public void checkAddInvalidStudent() {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("test123");
        studentDto.setLastName("test123");
        studentDto.setClassName("testA");
        studentDto.setBirthDay(LocalDate.now());

        when(studentService.addStudent(any(StudentDto.class))).thenReturn(null);

        ResponseEntity<ResponseDto> expected = ResponseEntity.badRequest().body(new ResponseDto(false, "Invalid student provided"));
        ResponseEntity<ResponseDto> actual = studentController.addStudent(studentDto);

        Assertions.assertNotNull(expected.getBody());
        Assertions.assertNotNull(actual.getBody());
        Assertions.assertEquals(expected.getStatusCode(), actual.getStatusCode());
        Assertions.assertEquals(expected.getBody().getMessage(), actual.getBody().getMessage());
    }

    @Test
    public void checkAddValidGradeWithValidStudent() {
        StudentGradeDto studentGradeDto = new StudentGradeDto();
        studentGradeDto.setStudentId("test");
        studentGradeDto.setGrade(new SubjectGradeDto(new TeacherEntity(), 10, LocalDate.now()));

        ResponseEntity<ResponseDto> expected = ResponseEntity.ok(new ResponseDto(true, "Grade added successfully"));

        when(studentService.addGrade(any(StudentGradeDto.class))).thenReturn(new ResponseDto(true, "Grade added successfully"));

        ResponseEntity<ResponseDto> actual = studentController.addGrade(studentGradeDto);

        Assertions.assertNotNull(expected.getBody());
        Assertions.assertNotNull(actual.getBody());
        Assertions.assertEquals(expected.getStatusCode(), actual.getStatusCode());
        Assertions.assertEquals(expected.getBody().getMessage(), actual.getBody().getMessage());

    }

    @Test
    public void checkAddValidGradeWithInvalidStudent() {
        StudentGradeDto studentGradeDto = new StudentGradeDto();
        studentGradeDto.setGrade(new SubjectGradeDto(new TeacherEntity(), 10, LocalDate.now()));

        ResponseEntity<ResponseDto> expected = ResponseEntity.badRequest().body(new ResponseDto(false, "Grade could not be added successfully"));

        when(studentService.addGrade(any(StudentGradeDto.class))).thenReturn(new ResponseDto(false, "Grade could not be added successfully"));

        ResponseEntity<ResponseDto> actual = studentController.addGrade(studentGradeDto);

        Assertions.assertNotNull(expected.getBody());
        Assertions.assertNotNull(actual.getBody());
        Assertions.assertEquals(expected.getStatusCode(), actual.getStatusCode());
        Assertions.assertEquals(expected.getBody().getMessage(), actual.getBody().getMessage());

    }

    @Test
    public void checkkGetGradesWithValidStudentId() {
        String studentId = "test";

        ResponseEntity<List<SubjectGradeDto>> expected = ResponseEntity.ok(new ArrayList<SubjectGradeDto>());

        when(studentService.getGradesByStudentId(any(String.class))).thenReturn(new ArrayList<SubjectGradeDto>());

        ResponseEntity<List<SubjectGradeDto>> actual = studentController.getStudentGrades(studentId);

        Assertions.assertNotNull(expected.getBody());
        Assertions.assertNotNull(actual.getBody());
        Assertions.assertEquals(expected.getStatusCode(), actual.getStatusCode());
        Assertions.assertEquals(expected.getBody(), actual.getBody());

    }

    @Test
    public void checkGetGradesWithInvalidStudentId() {
        ResponseEntity<List<SubjectGradeDto>> expected = ResponseEntity.ok(null);

        when(studentService.getGradesByStudentId(any(String.class))).thenReturn(null);

        ResponseEntity<List<SubjectGradeDto>> actual = studentController.getStudentGrades("");

        Assertions.assertEquals(expected.getStatusCode(), actual.getStatusCode());
        Assertions.assertEquals(expected.getBody(), actual.getBody());

    }


}
