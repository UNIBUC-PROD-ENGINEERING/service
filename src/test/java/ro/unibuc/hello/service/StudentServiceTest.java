package ro.unibuc.hello.service;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import ro.unibuc.hello.dto.ResponseDto;
import ro.unibuc.hello.dto.StudentDto;
import ro.unibuc.hello.dto.StudentGradeDto;
import ro.unibuc.hello.dto.SubjectGradeDto;
import ro.unibuc.hello.models.CatalogEntity;
import ro.unibuc.hello.models.StudentEntity;
import ro.unibuc.hello.models.TeacherEntity;
import ro.unibuc.hello.repositories.CatalogRepository;
import ro.unibuc.hello.repositories.StudentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;
    @Mock
    CatalogRepository catalogRepository;

    @Spy
    ModelMapper modelMapper;

    @InjectMocks
    StudentServiceImpl studentService;

    @Test
    public void checkAddCorrectlyFormattedStudent() {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("guguta");
        studentDto.setLastName("gicuta");
        studentDto.setClassName("5A");
        studentDto.setBirthDay(LocalDate.of(2000,12,12));

        StudentEntity newStudent = modelMapper.map(studentDto, StudentEntity.class);
        newStudent.setId(new ObjectId().toString());

        when(studentRepository.save(any(StudentEntity.class))).thenReturn(newStudent);

        StudentEntity entity = studentService.addStudent(studentDto);

        Assertions.assertNotNull(entity);
    }

    @Test
    public void checkAddStudentWithNumbersInFirstName() {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("guguta123");
        studentDto.setLastName("gicuta");
        studentDto.setClassName("5A");
        studentDto.setBirthDay(LocalDate.of(2000,12,12));

        StudentEntity newStudent = modelMapper.map(studentDto, StudentEntity.class);
        newStudent.setId(new ObjectId().toString());

        when(studentRepository.save(any(StudentEntity.class))).thenReturn(newStudent);

        StudentEntity entity = studentService.addStudent(studentDto);

        Assertions.assertNull(entity);
    }

    @Test
    public void checkAddStudentWithNumbersInLastName() {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("guguta");
        studentDto.setLastName("gicuta342");
        studentDto.setClassName("5A");
        studentDto.setBirthDay(LocalDate.of(2000,12,12));

        StudentEntity newStudent = modelMapper.map(studentDto, StudentEntity.class);
        newStudent.setId(new ObjectId().toString());

        when(studentRepository.save(any(StudentEntity.class))).thenReturn(newStudent);

        StudentEntity entity = studentService.addStudent(studentDto);

        Assertions.assertNull(entity);
    }


    @Test
    public void checkAddEmptyFormattedStudent() {
        StudentDto studentDto = new StudentDto();
        StudentEntity newStudent = modelMapper.map(studentDto, StudentEntity.class);
        when(studentRepository.save(any(StudentEntity.class))).thenReturn(newStudent);
        StudentEntity entity = studentService.addStudent(studentDto);
        Assertions.assertNull(entity);
    }

    @Test
    public void checkAddIncompleteFormattedStudent() {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("gugutaIncomplet");
        studentDto.setLastName("gicutaIncomplet");
        StudentEntity newStudent = modelMapper.map(studentDto, StudentEntity.class);
        when(studentRepository.save(any(StudentEntity.class))).thenReturn(newStudent);
        StudentEntity entity = studentService.addStudent(studentDto);
        Assertions.assertNull(entity);
    }

    @Test
    public void checkAddStudentOnlyWithId() {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(new ObjectId().toString());
        StudentEntity newStudent = modelMapper.map(studentDto, StudentEntity.class);
        when(studentRepository.save(any(StudentEntity.class))).thenReturn(newStudent);
        StudentEntity entity = studentService.addStudent(studentDto);
        Assertions.assertNull(entity);
    }

    @Test
    public void checkGetGradesWithValidData() {
        StudentEntity student = new StudentEntity();
        student.setId("test");
        CatalogEntity catalog = new CatalogEntity();
        TeacherEntity teacher = new TeacherEntity();
        teacher.setId("teachertest");
        SubjectGradeDto toCompare = new SubjectGradeDto(teacher, 1, LocalDate.now());
        catalog.addGrade(toCompare);
        catalog.setStudent(student);
        when(studentRepository.findById(any(String.class))).thenReturn(Optional.of(student));
        when(catalogRepository.findByStudent(any(StudentEntity.class))).thenReturn(catalog);

        List<SubjectGradeDto> result = studentService.getGradesByStudentId(student.getId());
        Assertions.assertEquals(toCompare, result.get(0));
    }

    @Test
    public void checkGetGradesWithoutValidStudent() {
        StudentEntity studentEntity = new StudentEntity();
        when(studentRepository.findById(any(String.class))).thenReturn(Optional.of(studentEntity));
        List<SubjectGradeDto> result = studentService.getGradesByStudentId(studentEntity.getId());
        Assertions.assertNull(result);
    }

    @Test
    public void checkGetGradesWithoutValidCatalog() {
        StudentEntity student = new StudentEntity();
        student.setId("test");
        when(studentRepository.findById(any(String.class))).thenReturn(Optional.of(student));
        when(catalogRepository.findByStudent(any(StudentEntity.class))).thenReturn(null);

        List<SubjectGradeDto> result = studentService.getGradesByStudentId(student.getId());
        Assertions.assertNull(result);
    }

    @Test
    public void checkGetGradesWithEmptyCatalog() {
        StudentEntity student = new StudentEntity();
        student.setId("test");
        CatalogEntity catalog = new CatalogEntity();
        when(studentRepository.findById(any(String.class))).thenReturn(Optional.of(student));
        when(catalogRepository.findByStudent(any(StudentEntity.class))).thenReturn(catalog);

        List<SubjectGradeDto> result = studentService.getGradesByStudentId(student.getId());
        Assertions.assertEquals(new ArrayList<>(), result);
    }

    @Test
    public void checkAddGradeWithEmptyCatalog() {
        StudentEntity studentEntity = new StudentEntity("test","test","test",LocalDate.now());
        studentEntity.setId("test");
        when(studentRepository.findById(any(String.class))).thenReturn(Optional.of(studentEntity));
        CatalogEntity catalogEntity = new CatalogEntity();
        when(catalogRepository.findByStudent(any(StudentEntity.class))).thenReturn(null);
        when(catalogRepository.save(any(CatalogEntity.class))).thenReturn(catalogEntity);

        ResponseDto response =studentService.addGrade(new StudentGradeDto(
                studentEntity.getId(),
                new SubjectGradeDto(new TeacherEntity(), 10, LocalDate.now())));

        Assertions.assertTrue(response.isSuccess());

    }

    @Test
    public void checkAddGradeWithACatalog() {
        StudentEntity studentEntity = new StudentEntity("test","test","test",LocalDate.now());
        studentEntity.setId("test");
        CatalogEntity catalogEntity = new CatalogEntity();
        when(catalogRepository.findByStudent(any(StudentEntity.class))).thenReturn(catalogEntity);
        when(studentRepository.findById(any(String.class))).thenReturn(Optional.of(studentEntity));

        when(catalogRepository.save(any(CatalogEntity.class))).thenReturn(catalogEntity);

        ResponseDto response =studentService.addGrade(new StudentGradeDto(
                studentEntity.getId(),
                new SubjectGradeDto(new TeacherEntity(), 10, LocalDate.now())));

        Assertions.assertTrue(response.isSuccess());

    }

    @Test
    public void checkAddGradeWithAnInvalidStudent() {
        StudentEntity studentEntity = new StudentEntity("test","test","test",LocalDate.now());
        studentEntity.setId("test");
        when(studentRepository.findById(any(String.class))).thenReturn(Optional.empty());

        ResponseDto response =studentService.addGrade(new StudentGradeDto(
                studentEntity.getId(),
                new SubjectGradeDto(new TeacherEntity(), 10, LocalDate.now())));

        Assertions.assertFalse(response.isSuccess());

    }

}
