package ro.unibuc.hello.exception;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends StoreException {

    public EntityAlreadyExistsException() {
        this.setHttpStatus(HttpStatus.CONFLICT);
        this.setMessage("Entity already exists");
    }
}

