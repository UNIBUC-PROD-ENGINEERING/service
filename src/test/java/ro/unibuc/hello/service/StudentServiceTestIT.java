package ro.unibuc.hello.service;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.unibuc.hello.dto.StudentDto;
import ro.unibuc.hello.models.StudentEntity;
import ro.unibuc.hello.repositories.StudentRepository;

import java.time.LocalDate;
import java.util.Objects;

@SpringBootTest
@Tag("IT")
public class StudentServiceTestIT {

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public StudentService studentService;

    @Autowired
    public StudentRepository studentRepository;

    @Test
    public void addStudent() {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(new ObjectId().toString());
        studentDto.setFirstName("TestIT");
        studentDto.setLastName("LastTestIT");
        studentDto.setClassName("10A");
        studentDto.setBirthDay(LocalDate.now());

        StudentEntity expected = modelMapper.map(studentDto, StudentEntity.class);

        StudentEntity actual = studentService.addStudent(studentDto);

        Assertions.assertEquals(expected, actual);

        if (actual != null) {
            studentRepository.delete(actual);
        }
    }

}
