package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.unibuc.hello.data.FoodEntity;
import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.repositories.FoodRepository;
import ro.unibuc.hello.repositories.PartyRepository;
import ro.unibuc.hello.service.PartyService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

    @Mock
    private PartyRepository partyRepository;

    @Mock
    private FoodRepository foodRepository;

    @InjectMocks
    private PartyService partyService;

    private PartyEntity party;
    private FoodEntity food1, food2;

    @BeforeEach
    void setUp() {
        party = new PartyEntity("Test Party", "2025-03-24");
        party.setId("party123");
        party.setPartyPoints(100);

        food1 = new FoodEntity("Pizza", 30.0, 4.5, 80);
        food1.setId("food1");
        
        food2 = new FoodEntity("Burger", 50.0, 4.0, 100);
        food2.setId("food2");
    }

    @Test
    void testGetAvailableFoodsForParty() {
        when(partyRepository.findById("party123")).thenReturn(Optional.of(party));
        when(foodRepository.findAll()).thenReturn(Arrays.asList(food1, food2));

        List<FoodEntity> result = partyService.getAvailableFoodsForParty("party123", 4.0, 40.0, 100);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pizza", result.get(0).getName());
    }

    @Test
    void testGetAvailableFoodsForParty_NoMatchingFoods() {
        when(partyRepository.findById("party123")).thenReturn(Optional.of(party));
        when(foodRepository.findAll()).thenReturn(Collections.emptyList());

        List<FoodEntity> result = partyService.getAvailableFoodsForParty("party123", 4.0, 40.0, 100);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testAddFoodToParty() {
        when(partyRepository.findById("party123")).thenReturn(Optional.of(party));
        when(foodRepository.findById("food1")).thenReturn(Optional.of(food1));
        when(partyRepository.save(any(PartyEntity.class))).thenReturn(party);

        PartyEntity updatedParty = partyService.addFoodToParty("party123", "food1");

        assertNotNull(updatedParty);
        assertTrue(updatedParty.getFoodIds().contains("food1"));
    }

    @Test
    void testAddFoodToParty_FoodNotFound() {
        when(partyRepository.findById("party123")).thenReturn(Optional.of(party));
        when(foodRepository.findById("food1")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            partyService.addFoodToParty("party123", "food1")
        );

        assertEquals("Food not found", exception.getMessage());
    }


    @Test
    void testRemoveFoodFromParty() {
        party.getFoodIds().add("food1");
        when(partyRepository.findById("party123")).thenReturn(Optional.of(party));
        when(partyRepository.save(any(PartyEntity.class))).thenReturn(party);

        PartyEntity updatedParty = partyService.removeFoodFromParty("party123", "food1");

        assertNotNull(updatedParty);
        assertFalse(updatedParty.getFoodIds().contains("food1"));
    }

    
}
