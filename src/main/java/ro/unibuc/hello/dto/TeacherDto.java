package ro.unibuc.hello.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDto {
    public String firstName;
    public String lastName;
    public String subject;

    public TeacherDto() {}
    public TeacherDto(String firstName, String lastName, String subject) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }
}
