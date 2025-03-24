package ro.unibuc.hello.exception;

/**
 * Exception class for bid-related errors in the auction system.
 * Provides static factory methods for common bid error scenarios.
 */
public class BidException extends RuntimeException {

    private BidException(String message) {
        super(message);
    }

    /**
     * Factory method for when the item for a bid doesn't exist.
     *
     * @return a new BidException with appropriate message
     */
    public static BidException itemNotFound() {
        return new BidException("Item not found");
    }

    /**
     * Factory method for when the item is not active.
     *
     * @return a new BidException with appropriate message
     */
    public static BidException itemNotActive() {
        return new BidException("Item is not active");
    }

    /**
     * Factory method for when the item's auction time has expired.
     *
     * @return a new BidException with appropriate message
     */
    public static BidException itemExpired() {
        return new BidException("Bidding time has expired for this item");
    }

    /**
     * Factory method for when the bid amount is too low.
     *
     * @return a new BidException with appropriate message
     */
    public static BidException bidTooLow() {
        return new BidException("Bid amount must be higher than the current highest bid");
    }
}