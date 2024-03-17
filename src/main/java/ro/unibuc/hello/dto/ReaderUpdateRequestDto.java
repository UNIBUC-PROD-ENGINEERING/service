package ro.unibuc.hello.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReaderUpdateRequestDto {

    private String email;
    private String phoneNumber;
}
