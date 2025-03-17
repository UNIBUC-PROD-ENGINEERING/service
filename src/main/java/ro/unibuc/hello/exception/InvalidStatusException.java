package ro.unibuc.hello.exception;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String status) {
        super("Invalid status: " + status + ". Allowed statuses: PENDING, IN_PROGRESS, COMPLETED, CANCELED, ERROR.");
    }
}
