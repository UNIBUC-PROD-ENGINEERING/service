package ro.unibuc.hello.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="Entity already exists")
public class EntityAlreadyExistsException extends RuntimeException {
    private static final String entityAlreadyExistsTemplate = "Entity: %s already exists";

    public EntityAlreadyExistsException(String entity) {
        super(String.format(entityAlreadyExistsTemplate, entity));
    }
}
