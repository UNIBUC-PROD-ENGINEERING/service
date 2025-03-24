package ro.unibuc.hello.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import ro.unibuc.hello.data.FoodEntity;
import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.repositories.FoodRepository;
import ro.unibuc.hello.repositories.PartyRepository;
import ro.unibuc.hello.service.PartyService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class FoodControllerTest {

    @Mock
    private PartyRepository partyRepository;

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private PartyService partyService;

    @InjectMocks
    private PartyController partyController;

    private PartyEntity testParty;
    private FoodEntity food1;
    private FoodEntity food2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testParty = new PartyEntity("Birthday Bash", "2025-05-15");
        testParty.setId("party123");
        testParty.setPartyPoints(100);

        food1 = new FoodEntity("Pizza", 8.5, 15.0, 30);
        food1.setId("food1");

        food2 = new FoodEntity("Sushi", 9.0, 25.0, 50);
        food2.setId("food2");
    }

    @Test
    void testGetAvailableFoodsForParty() {
        when(partyService.getAvailableFoodsForParty("party123", 8.0, 20.0, 40)).thenReturn(Arrays.asList(food1));

        List<FoodEntity> result = partyController.getAvailableFoodsForParty("party123", 8.0, 20.0, 40);

        assertEquals(1, result.size());
        assertEquals("food1", result.get(0).getId());
    }

    @Test
    void testGetAvailableFoodsForParty_NoMatches() {
        when(partyService.getAvailableFoodsForParty("party123", 5.0, 10.0, 20)).thenReturn(Collections.emptyList());

        List<FoodEntity> result = partyController.getAvailableFoodsForParty("party123", 5.0, 10.0, 20);

        assertTrue(result.isEmpty());
    }

    @Test
    void testAddFoodToParty() {
        when(partyService.addFoodToParty("party123", "food1")).thenReturn(testParty);

        ResponseEntity<PartyEntity> response = partyController.addFoodToParty("party123", "food1");

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testAddFoodToParty_NotFound() {
        when(partyService.addFoodToParty("party123", "food3")).thenReturn(null);

        ResponseEntity<PartyEntity> response = partyController.addFoodToParty("party123", "food3");

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testRemoveFoodFromParty() {
        when(partyService.removeFoodFromParty("party123", "food1")).thenReturn(testParty);

        ResponseEntity<PartyEntity> response = partyController.removeFoodFromParty("party123", "food1");

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());
    }

    // @Test
    // void testRemoveFoodFromParty_NotFound() {
    //     when(partyService.removeFoodFromParty("party123", "food3")).thenReturn(null);

    //     ResponseEntity<PartyEntity> response = partyController.removeFoodFromParty("party123", "food3");

    //     assertEquals(404, response.getStatusCode().value());
    // }
}
