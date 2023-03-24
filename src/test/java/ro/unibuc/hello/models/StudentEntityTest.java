package ro.unibuc.hello.models;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.unibuc.hello.dto.StudentDto;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentEntityTest {
    StudentEntity student = new StudentEntity("guguta","gicuta","5A",LocalDate.of(2000,12,12));

    @Test
    public void checkIfToStringContainsAllAttributesWithoutId() {
        String message = student.toString();
        student.setId("test");
        Assertions.assertTrue(message.contains(student.getFirstName()) && message.contains(student.getLastName())
        && message.contains(student.getClassName()) && message.contains(student.getBirthDay().toString()) && !message.contains(student.getId()));
    }
}
