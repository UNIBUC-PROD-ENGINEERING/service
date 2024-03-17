package ro.unibuc.hello.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthorCreationRequestDto {
    private String name;
    private String nationality;
}
