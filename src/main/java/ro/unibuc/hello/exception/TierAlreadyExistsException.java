package ro.unibuc.hello.exception;

public class TierAlreadyExistsException extends Exception {
    public TierAlreadyExistsException(String message) {
        super(message);
    }
}