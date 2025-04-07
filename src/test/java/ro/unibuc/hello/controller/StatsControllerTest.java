package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.unibuc.hello.dto.AuctionStats;
import ro.unibuc.hello.dto.ItemPopularity;
import ro.unibuc.hello.dto.UserStats;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.StatsService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatsControllerTest {

    @Mock
    private StatsService statsService;

    @InjectMocks
    private StatsController statsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getOverallStats_ShouldReturnStats() {
        // Arrange
        AuctionStats stats = new AuctionStats();
        when(statsService.getOverallStats()).thenReturn(stats);

        // Act
        ResponseEntity<AuctionStats> response = statsController.getOverallStats();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(statsService).getOverallStats();
    }

    @Test
    void getUserStats_ShouldReturnStats_WhenEmailIsValid() {
        // Arrange
        String email = "test@example.com";
        UserStats stats = new UserStats();
        stats.setEmail(email);
        when(statsService.getUserStats(email)).thenReturn(stats);

        // Act
        ResponseEntity<UserStats> response = statsController.getUserStats(email);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(email, response.getBody().getEmail());
        verify(statsService).getUserStats(email);
    }

    @Test
    void getUserStats_ShouldReturnBadRequest_WhenEmailIsInvalid() {
        // Arrange
        String email = "invalid-email";
        when(statsService.getUserStats(email)).thenThrow(new IllegalArgumentException("Invalid email format"));

        // Act
        ResponseEntity<UserStats> response = statsController.getUserStats(email);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(statsService).getUserStats(email);
    }

    @Test
    void getItemPopularity_ShouldReturnStats_WhenItemExists() {
        // Arrange
        String itemId = "item1";
        ItemPopularity popularity = new ItemPopularity();
        popularity.setItemId(itemId);
        when(statsService.getItemPopularity(itemId)).thenReturn(popularity);

        // Act
        ResponseEntity<ItemPopularity> response = statsController.getItemPopularity(itemId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(itemId, response.getBody().getItemId());
        verify(statsService).getItemPopularity(itemId);
    }

    @Test
    void getItemPopularity_ShouldReturnNotFound_WhenItemNotExists() {
        // Arrange
        String itemId = "nonexistent";
        when(statsService.getItemPopularity(itemId)).thenThrow(new EntityNotFoundException(itemId));

        // Act
        ResponseEntity<ItemPopularity> response = statsController.getItemPopularity(itemId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(statsService).getItemPopularity(itemId);
    }

    @Test
    void getCategoryStats_ShouldReturnStats_WhenCategoryIsValid() {
        // Arrange
        String category = "ELECTRONICS";
        AuctionStats stats = new AuctionStats();
        when(statsService.getCategoryStats(category)).thenReturn(stats);

        // Act
        ResponseEntity<AuctionStats> response = statsController.getCategoryStats(category);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(statsService).getCategoryStats(category);
    }

    @Test
    void getCategoryStats_ShouldReturnBadRequest_WhenCategoryIsInvalid() {
        // Arrange
        String category = "INVALID";
        when(statsService.getCategoryStats(category)).thenThrow(new IllegalArgumentException("Invalid category"));

        // Act
        ResponseEntity<AuctionStats> response = statsController.getCategoryStats(category);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(statsService).getCategoryStats(category);
    }

    @Test
    void getPopularItems_ShouldReturnItems() {
        // Arrange
        int limit = 5;
        List<ItemPopularity> popularItems = new ArrayList<>();
        ItemPopularity item1 = new ItemPopularity();
        item1.setItemId("item1");
        popularItems.add(item1);
        when(statsService.getPopularItems(limit)).thenReturn(popularItems);

        // Act
        ResponseEntity<List<ItemPopularity>> response = statsController.getPopularItems(limit);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(statsService).getPopularItems(limit);
    }

    @Test
    void getPopularCategories_ShouldReturnCategories() {
        // Arrange
        Map<String, Double> categories = new HashMap<>();
        categories.put("ELECTRONICS", 0.75);
        categories.put("BOOKS", 0.5);
        when(statsService.getPopularCategories()).thenReturn(categories);

        // Act
        ResponseEntity<Map<String, Double>> response = statsController.getPopularCategories();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().containsKey("ELECTRONICS"));
        verify(statsService).getPopularCategories();
    }

    @Test
    void getMostActiveUsers_ShouldReturnUsers() {
        // Arrange
        int limit = 5;
        List<UserStats> activeUsers = new ArrayList<>();
        UserStats user1 = new UserStats();
        user1.setEmail("user@example.com");
        activeUsers.add(user1);
        when(statsService.getMostActiveUsers(limit)).thenReturn(activeUsers);

        // Act
        ResponseEntity<List<UserStats>> response = statsController.getMostActiveUsers(limit);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(statsService).getMostActiveUsers(limit);
    }

    @Test
    void getBiddingHourDistribution_ShouldReturnDistribution() {
        // Arrange
        Map<String, Integer> distribution = new HashMap<>();
        distribution.put("9", 10);
        distribution.put("14", 15);
        when(statsService.getBiddingHourDistribution()).thenReturn(distribution);

        // Act
        ResponseEntity<Map<String, Integer>> response = statsController.getBiddingHourDistribution();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(10, response.getBody().get("9"));
        verify(statsService).getBiddingHourDistribution();
    }

    @Test
    void getHotItems_ShouldReturnItems() {
        // Arrange
        List<ItemPopularity> hotItems = new ArrayList<>();
        ItemPopularity item1 = new ItemPopularity();
        item1.setItemId("item1");
        item1.setHot(true);
        hotItems.add(item1);
        when(statsService.getHotItems()).thenReturn(hotItems);

        // Act
        ResponseEntity<List<ItemPopularity>> response = statsController.getHotItems();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertTrue(response.getBody().get(0).isHot());
        verify(statsService).getHotItems();
    }
}