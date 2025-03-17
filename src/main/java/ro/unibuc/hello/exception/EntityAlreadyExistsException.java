package ro.unibuc.hello.exception;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends StoreException {

    public EntityAlreadyExistsException() {
        this.setHttpStatus(HttpStatus.BAD_REQUEST);
        this.setMessage("Entity already exists");
    }
}

