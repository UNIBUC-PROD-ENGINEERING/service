package ro.unibuc.tbd.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.tbd.model.Client;
import ro.unibuc.tbd.repository.ClientRepository;
import ro.unibuc.tbd.repository.MealRepository;

@SpringBootTest
class ClientServiceTest {

    @Mock
    ClientRepository clientRepository;

    @Mock
    MealRepository mealRepository;

    @Mock
    OrderService orderService;

    @InjectMocks
    ClientService clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId("gfdgdf4325s01583f06eff");
        client.setName("John");
        client.setEmail("john@gmail.com");
        client.setAddress("284 Garfield Ave. Hackensack, NJ 07601");
        client.setPhoneNumber("0762476343");
        client.setCart(Map.of("622ac0a71068101583f06eff", 1));
    }

    @Test
    void getClientById() {
    }

    @Test
    void createClient() {
        // Arrange
        when(clientRepository.save(client)).thenReturn(client);

        // Act
        Client result = clientService.createClient(client);

        // Assert
        assertEquals(result.getName(), client.getName());
        assertEquals(result.getEmail(), client.getEmail());
        assertEquals(result.getAddress(), client.getAddress());
        assertEquals(result.getPhoneNumber(), client.getPhoneNumber());
        assertNotNull(result.getCart());
    }

    @Test
    void updateClient() {
    }

    @Test
    void deleteClientById() {
    }

    @Test
    void addToCart() {
    }

    @Test
    void removeFromCart() {
    }

    @Test
    void clearCart() {
    }

    @Test
    void placeOrder() {
    }
}