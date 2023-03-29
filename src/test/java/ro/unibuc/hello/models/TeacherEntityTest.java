package ro.unibuc.hello.models;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeacherEntityTest {
    TeacherEntity teacher = new TeacherEntity("test", "test", "info");

    @Test
    public void checkIfToStringContainsAllAttributesWithoutId() {
        String message = teacher.toString();
        teacher.setId("test");
        Assertions.assertTrue(message.contains(teacher.getFirstName()) && message.contains(teacher.getLastName())
        && message.contains(teacher.getSubject()) && !message.contains(teacher.getId()));
    }
}
