package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.dto.Subscription;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.TierAlreadyExistsException;
import ro.unibuc.hello.service.SubscriptionService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubscriptionControllerTest {
    
    @Mock
    private SubscriptionService subscriptionService;

    @InjectMocks
    private SubscriptionController subscriptionController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController).build();
    }

    @Test
    void testGetAllSubscriptions() throws Exception {
        // Arrange
        List<Subscription> subscriptions = Arrays.asList(
            new Subscription(1, 10),
            new Subscription(2, 20)
        );
        when(subscriptionService.getAllSubscriptions()).thenReturn(subscriptions);

        // Act & Assert
        mockMvc.perform(get("/subscriptions"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].tier").value(1))
            .andExpect(jsonPath("$[0].price").value(10))
            .andExpect(jsonPath("$[1].tier").value(2))
            .andExpect(jsonPath("$[1].price").value(20));
    }

    @Test
    void testGetSubscriptionById() throws Exception {
        // Arrange
        String id = "123";
        List<Subscription> subscription = Collections.singletonList(new Subscription(1, 10));
        when(subscriptionService.getSubscriptionById(id)).thenReturn(subscription);

        // Act & Assert
        mockMvc.perform(get("/subscriptions").param("id", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].tier").value(1))
            .andExpect(jsonPath("$[0].price").value(10));
    }

    @Test
    void testGetSubscriptionsByTier() throws Exception {
        // Arrange
        int tier = 1;
        List<Subscription> subscriptions = Collections.singletonList(new Subscription(tier, 10));
        when(subscriptionService.getSubscriptionsByTier(tier)).thenReturn(subscriptions);

        // Act & Assert
        mockMvc.perform(get("/subscriptions").param("tier", String.valueOf(tier)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].tier").value(tier))
            .andExpect(jsonPath("$[0].price").value(10));
    }

    @Test
    void testGetSubscriptionsUpToTier() throws Exception {
        // Arrange
        int tier = 2;
        List<Subscription> subscriptions = Arrays.asList(
            new Subscription(1, 10),
            new Subscription(2, 20)
        );
        when(subscriptionService.getSubscriptionsUpToTier(tier)).thenReturn(subscriptions);

        // Act & Assert
        mockMvc.perform(get("/subscriptions/uptotier").param("tier", String.valueOf(tier)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].tier").value(1))
            .andExpect(jsonPath("$[0].price").value(10))
            .andExpect(jsonPath("$[1].tier").value(2))
            .andExpect(jsonPath("$[1].price").value(20));
    }

    @Test
    void testGetSubscriptionsByMaxPrice() throws Exception {
        // Arrange
        int price = 15;
        List<Subscription> subscriptions = Arrays.asList(
            new Subscription(1, 10),
            new Subscription(2, 15),

            new Subscription(3, 20)
        );
        when(subscriptionService.getSubscriptionsByMaxPrice(price)).thenReturn(subscriptions);

        // Act & Assert
        mockMvc.perform(get("/subscriptions?price=15"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].tier").value(1))
            .andExpect(jsonPath("$[0].price").value(10))
            .andExpect(jsonPath("$[1].tier").value(2))
            .andExpect(jsonPath("$[1].price").value(15));
    }

    @Test
    void testGetSubscriptionsByTierAndMaxPrice() throws Exception {
        // Arrange
        List<Subscription> subscriptions = Collections.singletonList(
            new Subscription(2, 20)
        );
        when(subscriptionService.getSubscriptionsByTierAndMaxPrice(2, 20)).thenReturn(subscriptions);

        // Act & Assert
        mockMvc.perform(get("/subscriptions?tier=2&price=20"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].tier").value(2))
            .andExpect(jsonPath("$[0].price").value(20));
    }

    @Test
    void testSaveSubscriptionSuccess() throws TierAlreadyExistsException {
        // Arrange
        Subscription subscription = new Subscription(1, 10);
        when(subscriptionService.saveSubscription(any(Subscription.class))).thenReturn(subscription);

        // Act
        ResponseEntity<?> response = subscriptionController.saveSubscription(1, 10);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(subscription, response.getBody());
    }

    @Test
    void testSaveSubscriptionWithDuplicateTier() throws TierAlreadyExistsException {
        // Arrange
        when(subscriptionService.saveSubscription(any(Subscription.class)))
            .thenThrow(new TierAlreadyExistsException("Tier 1 already exists"));

        // Act
        ResponseEntity<?> response = subscriptionController.saveSubscription(1, 10);

        // Assert
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Tier 1 already exists", response.getBody());
    }

    @Test
    void testDeleteSubscriptionSuccess() {
        // Arrange
        String id = "123";
        when(subscriptionService.deleteSubscription(id)).thenReturn(true);

        // Act
        String result = subscriptionController.deleteSubscription(id);

        // Assert
        assertEquals("Success.", result);
    }

    @Test
    void testDeleteSubscriptionNotFound() {
        // Arrange
        String id = "123";
        when(subscriptionService.deleteSubscription(id)).thenReturn(false);

        // Act
        String result = subscriptionController.deleteSubscription(id);

        // Assert
        assertEquals("No subscription with this id.", result);
    }
}
