package ro.unibuc.hello.exception;

public class EntityNotFoundException extends RuntimeException {
    private static final String entityNotFoundTemplate = "Entity: %s was not found";

    public EntityNotFoundException(String entity) {
        super(String.format(entityNotFoundTemplate, entity));
    }
}