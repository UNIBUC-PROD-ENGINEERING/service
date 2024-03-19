package ro.unibuc.triplea.domain.auth.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.unibuc.triplea.application.auth.dto.response.StandardResponse;
import ro.unibuc.triplea.domain.auth.exception.NotFoundException;
import ro.unibuc.triplea.domain.auth.exception.ValidateException;

@RestControllerAdvice
@CrossOrigin
public class AppWideExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardResponse> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<StandardResponse>(new StandardResponse("404", "Error", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<StandardResponse> handleValidationException(ValidateException e) {
        return new ResponseEntity<StandardResponse>(new StandardResponse("400", "Error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardResponse> handleException(Exception e) throws Exception {
        return new ResponseEntity<StandardResponse>(new StandardResponse("500", "Error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
