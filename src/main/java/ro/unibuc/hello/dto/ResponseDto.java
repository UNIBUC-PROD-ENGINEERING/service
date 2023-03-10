package ro.unibuc.hello.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    private boolean success;
    private String message;

    public ResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
