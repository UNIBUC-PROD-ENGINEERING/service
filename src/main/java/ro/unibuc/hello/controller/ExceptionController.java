package ro.unibuc.hello.controller;

import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ro.unibuc.hello.exception.EntityAlreadyExistsException;
import ro.unibuc.hello.exception.EntityNotFoundException;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
class ExceptionController {
    public class AuthExceptionHandlerController extends ResponseEntityExceptionHandler {

        @ExceptionHandler(value = {EntityAlreadyExistsException.class})
        public ResponseEntity<?> handleEntityAlreadyExistsException(EntityAlreadyExistsException exception,
                                                WebRequest request) {
            logger.warn(exception.getMessage());
            return new ResponseEntity<>(Map.of("message", "An entity with this code already exists."),
                    HttpStatus.CONFLICT);
        }

        @ExceptionHandler(value = {EntityNotFoundException.class})
        public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception,
                                                WebRequest request) {
            logger.warn(exception.getMessage());
            return new ResponseEntity<>(Map.of("message", "Entity was not found."),
                HttpStatus.NOT_FOUND);
        }
    }
}
