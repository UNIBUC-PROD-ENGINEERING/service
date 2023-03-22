package ro.unibuc.hello.dto;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

public class StudentTest {

    StudentDto student = new StudentDto("1", "Ana", "Buiciuc", "12C", LocalDate.of(2000, 12, 06));
    @Test
    public void test_first_name() {
        Assertions.assertSame("Ana", student.getFirstName());
    }
    @Test
    public void test_id() {
        Assertions.assertSame("1", student.getId());
    }
}
