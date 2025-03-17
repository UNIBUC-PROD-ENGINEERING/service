package ro.unibuc.hello.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EntityAlreadyExistsException extends StoreException {

    public EntityAlreadyExistsException() {
        this.setHttpStatus(HttpStatus.BAD_REQUEST);
        this.setMessage("Entity already exists");
    }
}

