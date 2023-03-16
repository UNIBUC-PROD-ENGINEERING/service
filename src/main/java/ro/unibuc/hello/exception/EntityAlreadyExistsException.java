package ro.unibuc.hello.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    private static final String entityAlreadyExistsTemplate = "Entity: %s already exists in the database";

    public EntityAlreadyExistsException(String entity) {
        super(String.format(entityAlreadyExistsTemplate, entity));
    }
}
