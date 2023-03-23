package ro.unibuc.hello.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDto {
    private String firstName;
    private String lastName;
    private String subject;

    public TeacherDto(String firstName, String lastName, String subject) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }
}
