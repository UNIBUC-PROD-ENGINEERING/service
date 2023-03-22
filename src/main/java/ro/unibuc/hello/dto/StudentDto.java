package ro.unibuc.hello.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentDto {
    private String id;
    private String firstName;
    private String lastName;
    private String className;
    private LocalDate birthDay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public StudentDto(String id, String firstName, String lastName, String className, LocalDate birthDay) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.className = className;
        this.birthDay = birthDay;
    }
}
