package ro.unibuc.hello.service;

import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.dto.TeacherDto;
import ro.unibuc.hello.models.TeacherEntity;
import ro.unibuc.hello.repositories.TeacherRepository;

@SpringBootTest
@Tag("IT")
public class TeacherServiceTestIT {
    @Autowired
    public TeacherRepository teacherRepository;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public TeacherService teacherService;

    @Test
    public void test_updateTeacherData() {
        TeacherEntity teacher = new TeacherEntity("Paul", "Marinescu", "Matematica");
        teacherRepository.save(teacher);

        TeacherDto teacherDto = modelMapper.map(teacher, TeacherDto.class);
        teacherDto.setFirstName("Gogu");

        teacherService.updateTeacherData(teacherDto, teacher.getId());

        teacher = teacherRepository.findByFirstNameAndLastName("Gogu", "Marinescu");

        teacherRepository.delete(teacher);

        Assertions.assertNotNull(teacher);
    }
}
