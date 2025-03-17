package ro.unibuc.hello.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String itemId, int available, int requested) {
        super("Insufficient stock for item " + itemId + ". Available: " + available + ", requested: " + requested);
    }
}
