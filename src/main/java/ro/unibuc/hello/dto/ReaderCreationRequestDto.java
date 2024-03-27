package ro.unibuc.hello.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReaderCreationRequestDto {
    private String name;
    private String nationality;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private LocalDate registrationDate;
}
