package ro.unibuc.hello.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    private static final String entityNotFoundTemplate = "Entity: %s was not found";

    public EntityNotFoundException(String entity) {
        super(String.format(entityNotFoundTemplate, entity));
    }
}
