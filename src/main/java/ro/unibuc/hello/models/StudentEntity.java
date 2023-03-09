package ro.unibuc.hello.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Getter
@Setter
public class StudentEntity {
    @Id
    public String id;

    public String firstName;
    public String lastName;
    public String className;
    public LocalDate birthDay;

    public StudentEntity() {}

    public StudentEntity(String firstName, String lastName, String className, LocalDate birthDay) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.className = className;
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return "Student[firstName='" + firstName + "', lastName='" + lastName + "', className='" + className + "', " +
                "birthDay='" + birthDay + "']";
    }
}
