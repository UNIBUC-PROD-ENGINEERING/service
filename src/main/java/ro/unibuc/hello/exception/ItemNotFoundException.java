package ro.unibuc.hello.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String itemId) {
        super("Item with ID " + itemId + " was not found in inventory.");
    }
}
