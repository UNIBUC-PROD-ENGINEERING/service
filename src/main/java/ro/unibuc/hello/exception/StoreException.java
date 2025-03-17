package ro.unibuc.hello.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoreException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;
}
