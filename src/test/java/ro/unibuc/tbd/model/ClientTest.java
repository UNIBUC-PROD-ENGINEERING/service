package ro.unibuc.tbd.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientTest {

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId("gfdgdf4325s01583f06eff");
        client.setName("John");
        client.setEmail("john@gmail.com");
        client.setAddress("284 Garfield Ave. Hackensack, NJ 07601");
        client.setPhoneNumber("0762476343");
    }

    @Test
    void updateClient() {
        // Arrange
        Client other = new Client();
        other.setName("Tom");
        other.setEmail("tom@gmail.com");
        other.setAddress("9573 Academy Ave. Johnson City, TN 37601");
        other.setPhoneNumber("072352352");

        // Act
        client.updateClient(other);

        // Assert
        assertEquals(client.getName(), other.getName());
        assertEquals(client.getEmail(), other.getEmail());
        assertEquals(client.getAddress(), other.getAddress());
        assertEquals(client.getPhoneNumber(), other.getPhoneNumber());
    }

    @Test
    void addToCart() {
        // Arrange
        String mealId = "622ac0a71068101583f06eff";

        // Act
        client.addToCart(mealId);

        // Assert
        assertTrue(client.getCart().containsKey(mealId));
    }
}