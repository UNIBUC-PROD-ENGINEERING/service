package ro.unibuc.slots.exception;

public class EntityNotFoundException extends RuntimeException {
    private static final String entityNotFoundTemplate = "Entity: %s was not found";

    public EntityNotFoundException(final String entity) {
        super(String.format(entityNotFoundTemplate, entity));
    }
}
