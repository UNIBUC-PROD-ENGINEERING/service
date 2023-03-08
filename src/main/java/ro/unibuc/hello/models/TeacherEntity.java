package ro.unibuc.hello.models;

import org.springframework.data.annotation.Id;

public class TeacherEntity {
    @Id
    public String id;

    public String firstName;
    public String lastName;
    public String subject;

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
