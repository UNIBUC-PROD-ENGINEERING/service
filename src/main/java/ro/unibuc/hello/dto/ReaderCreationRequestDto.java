package ro.unibuc.hello.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReaderCreationRequestDto {

    private String name;
    private String nationality;
    private String email;
    private Integer phoneNumber;
    private LocalDate birthDate;
    private LocalDate registrationDate;
}
