package ro.unibuc.hello.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReaderCreationRequestDto {
    private String name;
    private String nationality;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private LocalDate registrationDate;
}
