package ro.unibuc.hello.exception;

public class InvalidModelException extends RuntimeException {

    private static final String invalidModelTemplate = "The following object does not respect the model constraints: %s";

    public InvalidModelException(String entity) {
        super(String.format(invalidModelTemplate, entity));
    }
}
