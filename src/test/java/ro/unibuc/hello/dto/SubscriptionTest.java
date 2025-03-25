package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionTest {

    @Test
    void testSubscriptionCreation() {
        // Arrange & Act
        Subscription subscription = new Subscription(1, 10);

        // Assert
        assertEquals(1, subscription.getTier());
        assertEquals(10, subscription.getPrice());
    }

    @Test
    void testSubscriptionSetters() {
        // Arrange
        Subscription subscription = new Subscription();

        // Act
        subscription.setTier(2);
        subscription.setPrice(20);

        // Assert
        assertEquals(2, subscription.getTier());
        assertEquals(20, subscription.getPrice());
    }

    @Test
    void testDefaultConstructor() {
        // Arrange & Act
        Subscription subscription = new Subscription();

        // Assert
        assertEquals(0, subscription.getTier());
        assertEquals(0, subscription.getPrice());
    }
}
