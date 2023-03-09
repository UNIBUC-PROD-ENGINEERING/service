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
}
