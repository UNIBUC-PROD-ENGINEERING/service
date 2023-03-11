package ro.unibuc.hello.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class TeacherEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String subject;

    public TeacherEntity() {}

    public TeacherEntity(String firstName, String lastName, String subject) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Teacher[firstName='" + firstName + "', lastName='" + lastName + "', subject='" + subject + "']";
    }
}
