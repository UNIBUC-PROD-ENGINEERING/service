package ro.unibuc.hello.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Entity not found")
public class EntityNotFoundException extends RuntimeException {

    private static final String entityNotFoundTemplate = "Entity: %s was not found";

    public EntityNotFoundException(String entity) {
        super(String.format(entityNotFoundTemplate, entity));
    }
}
