package ro.unibuc.hello.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    private static final String message = "Entity already exists";

    public EntityAlreadyExistsException() {
        super(message);
    }
}

