package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BidTest {

    @Test
    void testDefaultConstructor() {
        // Act
        Bid bid = new Bid();

        // Assert
        assertNotNull(bid);

        // Default fields should be null/default
        assertNull(bid.getId());
        assertNull(bid.getItemId());
        assertNull(bid.getBidderName());
        assertNull(bid.getEmail());
        assertNull(bid.getCreatedAt());
        assertNull(bid.getItemName());
        assertEquals(0.0, bid.getAmount(), 0.001);
    }

    @Test
    void testParameterizedConstructor() {
        // Arrange
        String id = "bid123";
        String itemId = "item123";
        String bidderName = "John Doe";
        double amount = 123.45;
        LocalDateTime createdAt = LocalDateTime.now();
        String email = "john@example.com";

        // Act
        Bid bid = new Bid(id, itemId, bidderName, amount, createdAt, email);

        // Assert
        assertNotNull(bid);
        assertEquals(id, bid.getId());
        assertEquals(itemId, bid.getItemId());
        assertEquals(bidderName, bid.getBidderName());
        assertEquals(amount, bid.getAmount(), 0.001);
        assertEquals(createdAt, bid.getCreatedAt());
        assertEquals(email, bid.getEmail());
        assertNull(bid.getItemName());  // itemName is not set in this constructor
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        Bid bid = new Bid();
        String id = "bid456";
        String itemId = "item789";
        String bidderName = "Jane Doe";
        double amount = 678.90;
        LocalDateTime createdAt = LocalDateTime.now().minusHours(1);
        String itemName = "Test Item";
        String email = "jane@example.com";

        // Act
        bid.setId(id);
        bid.setItemId(itemId);
        bid.setBidderName(bidderName);
        bid.setAmount(amount);
        bid.setCreatedAt(createdAt);
        bid.setItemName(itemName);
        bid.setEmail(email);

        // Assert
        assertEquals(id, bid.getId());
        assertEquals(itemId, bid.getItemId());
        assertEquals(bidderName, bid.getBidderName());
        assertEquals(amount, bid.getAmount(), 0.001);
        assertEquals(createdAt, bid.getCreatedAt());
        assertEquals(itemName, bid.getItemName());
        assertEquals(email, bid.getEmail());
    }

    @Test
    void testBidWithItemName() {
        // Arrange
        Bid bid = new Bid();
        bid.setId("bid123");
        bid.setItemId("item123");
        bid.setBidderName("Test Bidder");
        bid.setAmount(150.0);

        // Act
        bid.setItemName("Special Item");

        // Assert
        assertEquals("Special Item", bid.getItemName());
    }

    @Test
    void testBidEmailField() {
        // Arrange
        Bid bid = new Bid();
        String email = "bidder@example.com";

        // Act
        bid.setEmail(email);

        // Assert
        assertEquals(email, bid.getEmail());
    }

    @Test
    void testBidConsistency() {
        // Arrange
        String id = "bid123";
        String itemId = "item123";
        String bidderName = "Test Bidder";
        double amount = 200.0;
        LocalDateTime createdAt = LocalDateTime.now();
        String email = "test@example.com";

        // Act - Create with constructor and then modify with setters
        Bid bid1 = new Bid(id, itemId, bidderName, amount, createdAt, email);

        Bid bid2 = new Bid();
        bid2.setId(id);
        bid2.setItemId(itemId);
        bid2.setBidderName(bidderName);
        bid2.setAmount(amount);
        bid2.setCreatedAt(createdAt);
        bid2.setEmail(email);

        // Assert - Core fields should match
        assertEquals(bid1.getId(), bid2.getId());
        assertEquals(bid1.getItemId(), bid2.getItemId());
        assertEquals(bid1.getBidderName(), bid2.getBidderName());
        assertEquals(bid1.getAmount(), bid2.getAmount(), 0.001);
        assertEquals(bid1.getCreatedAt(), bid2.getCreatedAt());
        assertEquals(bid1.getEmail(), bid2.getEmail());
    }
}