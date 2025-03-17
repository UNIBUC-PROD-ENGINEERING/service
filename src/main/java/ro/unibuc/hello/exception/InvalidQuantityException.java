package ro.unibuc.hello.exception;

public class InvalidQuantityException extends RuntimeException {
    public InvalidQuantityException(int quantity) {
        super("Invalid quantity: " + quantity + ". Quantity must be greater than 0.");
    }
}
