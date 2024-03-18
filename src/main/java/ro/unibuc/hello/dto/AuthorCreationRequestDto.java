package ro.unibuc.hello.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthorCreationRequestDto {
    private String name;
    private String nationality;
    private LocalDate birthDate;
    private LocalDate deathDate;
}
