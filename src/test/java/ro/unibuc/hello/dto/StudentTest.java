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
    @Test
    public void test_last_name() {
        Assertions.assertSame("Buiciuc", student.getLastName());
    }
    @Test
    public void test_date_of_birth() {
        LocalDate expectedDate = LocalDate.of(2000, 12, 06);
        Assertions.assertEquals(expectedDate, student.getBirthDay());
    }
}
