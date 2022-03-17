package ro.unibuc.tbd.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.tbd.model.CartRequestDTO;
import ro.unibuc.tbd.model.Client;
import ro.unibuc.tbd.model.Meal;
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
    private Meal meal;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId("gfdgdf4325s01583f06eff");
        client.setName("John");
        client.setEmail("john@gmail.com");
        client.setAddress("284 Garfield Ave. Hackensack, NJ 07601");
        client.setPhoneNumber("0762476343");

        meal = new Meal();
        meal.setId("fsdvsdjmfsd1234590231sd");
        meal.setName("Paste Carbonara");
        meal.setIngredients(List.of("Paste", "Bacon"));
        meal.setPrice((float) 27.99);
        meal.setPortionSize(400);
    }

    @Test
    void getClientById() {
        // Arrange
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(client));

        // Act
        Client result = clientService.getClientById(client.getId());

        assertEquals(result, client);
    }

    @Test
    void getClientByIdThrowsOnEmpty() {
        // Arrange
        when(clientRepository.findById(any())).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResponseStatusException.class,
                () -> clientService.getClientById(client.getId()),
                "Expected getClientById() to throw a ResponseStatusException, but it didn't."
        );
    }

    @Test
    void getAllClientsReturnsEmptyList() {
        // Arrange

        // Act
        var result = clientService.getAllClients();

        // Assert
        assertEquals(result.size(), 0);
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
        // Arrange
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(client));
        Client other = new Client();
        other.setName("Tom");
        other.setEmail("tom@gmail.com");
        other.setAddress("9573 Academy Ave. Johnson City, TN 37601");
        other.setPhoneNumber("072352352");

        // Act
        Client result = clientService.updateClient(client.getId(), other);

        // Assert
        verify(clientRepository, times(1)).save(any());
        assertEquals(result.getName(), other.getName());
        assertEquals(result.getEmail(), other.getEmail());
        assertEquals(result.getAddress(), other.getAddress());
        assertEquals(result.getPhoneNumber(), other.getPhoneNumber());
    }

    @Test
    void deleteClientById() {
        // Arrange
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(client));

        // Act
        Client result = clientService.deleteClientById(client.getId());

        // Assert
        verify(clientRepository, times(1)).deleteById(eq(client.getId()));
        assertEquals(result, client);
    }

    @Test
    void addToCart() {
        // Arrange
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(client));
        when(mealRepository.findById(any())).thenReturn(Optional.ofNullable(meal));

        CartRequestDTO cartRequest = new CartRequestDTO();
        cartRequest.setMealId(meal.getId());

        // Act
        Client result = clientService.addToCart(client.getId(), cartRequest);

        // Assert
        verify(clientRepository, times(1)).save(any());
        assertTrue(result.getCart().containsKey(meal.getId()));
    }

    @Test
    void addToCart_MealNotFound() {
        // Arrange
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(client));
        when(mealRepository.findById(any())).thenReturn(Optional.empty());

        CartRequestDTO cartRequest = new CartRequestDTO();
        cartRequest.setMealId(meal.getId());

        // Act + Assert
        assertThrows(ResponseStatusException.class,
                () -> clientService.addToCart(client.getId(), cartRequest),
                "Expected addToCart() to throw a ResponseStatusException, but it didn't.");
    }

    @Test
    void removeFromCart() {
        // Arrange
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(client));
        when(mealRepository.findById(any())).thenReturn(Optional.ofNullable(meal));

        CartRequestDTO cartRequest = new CartRequestDTO();
        cartRequest.setMealId(meal.getId());

        // Act
        clientService.addToCart(client.getId(), cartRequest);
        Client result = clientService.removeFromCart(client.getId(), cartRequest);

        // Assert
        verify(clientRepository, times(2)).save(any());
        assertFalse(result.getCart().containsKey(meal.getId()));
    }

    @Test
    void clearCart() {
        // Arrange
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(client));
        when(mealRepository.findById(any())).thenReturn(Optional.ofNullable(meal));

        CartRequestDTO cartRequest = new CartRequestDTO();
        cartRequest.setMealId(meal.getId());

        // Act
        clientService.addToCart(client.getId(), cartRequest);
        Client result = clientService.clearCart(client.getId());

        // Assert
        verify(clientRepository, times(2)).save(any());
        assertTrue(result.getCart().isEmpty());
    }

    @Test
    void placeOrder() {
        // Arrange
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(client));
        when(mealRepository.findById(any())).thenReturn(Optional.ofNullable(meal));

        CartRequestDTO cartRequest = new CartRequestDTO();
        cartRequest.setMealId(meal.getId());

        // Act
        clientService.addToCart(client.getId(), cartRequest);
        Client result = clientService.placeOrder(client.getId());

        // Assert
        verify(clientRepository, times(2)).save(any());
        verify(orderService, times(1)).createOrder(any());
        assertTrue(result.getCart().isEmpty());
    }
}