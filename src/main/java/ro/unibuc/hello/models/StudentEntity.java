package ro.unibuc.hello.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import ro.unibuc.hello.helpers.RegexFormatters;

import java.time.LocalDate;

@Getter
@Setter
public class StudentEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String className;
    private LocalDate birthDay;

    public StudentEntity() {
    }

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

    public static boolean validateStudent(StudentEntity student) {
        return student != null && student.getFirstName() != null && student.getLastName() != null &&
                student.getFirstName().matches(RegexFormatters.ONLY_LETTERS) &&
                student.getLastName().matches(RegexFormatters.ONLY_LETTERS) &&
                student.getClassName() != null && student.getBirthDay() != null;
    }
}
