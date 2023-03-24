package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherDtoTest {
    @Test
    public void testSettersAndGetters() {
        TeacherDto teacherDto
                = new TeacherDto("Andrei", "Popescu", "Algoritmi Paraleli" );

        String firstName = teacherDto.getFirstName();
        String lastName = teacherDto.getLastName();
        String subject = teacherDto.getSubject();

        assertEquals("Andrei", firstName);
        assertEquals("Popescu", lastName);
        assertEquals("Algoritmi Paraleli", subject);
    }
}