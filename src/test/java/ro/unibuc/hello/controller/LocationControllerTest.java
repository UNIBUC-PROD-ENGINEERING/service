package ro.unibuc.hello.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.unibuc.hello.data.LocationEntity;
import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.service.PartyService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class LocationControllerTest {

    @Mock
    private PartyService partyService;

    @InjectMocks
    private PartyController partyController;

    private PartyEntity testParty;
    private LocationEntity location1;
    private LocationEntity location2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testParty = new PartyEntity("Birthday Bash", "2025-05-15");
        testParty.setId("party123");
        testParty.setPartyPoints(100);

        location1 = new LocationEntity("Club X", "123 Main St", 200, 4.5, 50);
        location1.setId("loc1");

        location2 = new LocationEntity("Lounge Y", "456 Side St", 150, 4.0, 120);
        location2.setId("loc2");
    }

    @Test
    void testGetAvailableLocationsForParty() {
        when(partyService.getAvailableLocationsForParty("party123", 4.0, 180.0, 100))
                .thenReturn(Arrays.asList(location1));

        List<LocationEntity> result = partyController.getAvailableLocationsForParty("party123", 4.0, 180.0, 100);

        assertEquals(1, result.size());
        assertEquals("loc1", result.get(0).getId());
    }

    @Test
    void testGetAvailableLocationsForParty_NoResults() {
        when(partyService.getAvailableLocationsForParty("party123", 5.0, 50.0, 200))
                .thenReturn(Collections.emptyList());

        List<LocationEntity> result = partyController.getAvailableLocationsForParty("party123", 5.0, 50.0, 200);

        assertTrue(result.isEmpty());
    }

    @Test
    void testAddLocationToParty() {
        when(partyService.addLocationToParty("party123", "loc1")).thenReturn(testParty);

        ResponseEntity<PartyEntity> response = partyController.addLocationToParty("party123", "loc1");

        assertNotNull(response.getBody());
        assertEquals("party123", response.getBody().getId());
    }

    @Test
    void testAddLocationToParty_NotFound() {
        when(partyService.addLocationToParty("party123", "loc3")).thenReturn(null);

        ResponseEntity<PartyEntity> response = partyController.addLocationToParty("party123", "loc3");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testRemoveLocationFromParty() {
        when(partyService.removeLocationFromParty("party123")).thenReturn(testParty);

        ResponseEntity<PartyEntity> response = partyController.removeLocationFromParty("party123");

        assertNotNull(response.getBody());
        assertEquals("party123", response.getBody().getId());
    }

    
}
