package ro.unibuc.hello.exception;

public class DuplicateException extends RuntimeException {

    private static final String duplicateTemplate = "Entity: %s is duplicate!";

    public DuplicateException(String entity) {
        super(String.format(duplicateTemplate, entity));
    }
}
