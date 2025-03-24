package ro.unibuc.hello.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ro.unibuc.hello.controller.PartyController;
import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.repositories.PartyRepository;
import ro.unibuc.hello.service.PartyService;
import ro.unibuc.hello.data.FoodEntity;
import ro.unibuc.hello.data.LocationEntity;
import ro.unibuc.hello.repositories.FoodRepository;
import ro.unibuc.hello.repositories.LocationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PartyControllerTest {

    @Mock
    private PartyRepository partyRepository;

    @Mock
    private PartyService partyService;

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private PartyController partyController;

    private PartyEntity party;

    @BeforeEach
    void setUp() {
        party = new PartyEntity("Birthday Party", "2025-04-01");
        party.setId("party123");
    }

    @Test
    public void testGetAllParties_Success() {
        when(partyRepository.findAll()).thenReturn(List.of(party));

        List<?> response = partyController.getAllParties();

        assertEquals(1, response.size());
    }

    @Test
    public void testGetPartyById_Success() {
        when(partyRepository.findById("party123")).thenReturn(Optional.of(party));

        ResponseEntity<?> response = partyController.getPartyById("party123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetPartyById_NotFound() {
        when(partyRepository.findById("invalidId")).thenReturn(Optional.empty());

        ResponseEntity<?> response = partyController.getPartyById("invalidId");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateParty_Success() {
        when(partyRepository.save(any(PartyEntity.class))).thenReturn(party);

        PartyEntity result = partyController.createParty(party);

        assertEquals("Birthday Party", result.getName());
    }

    @Test
    public void testUpdateParty_Success() {
        when(partyRepository.save(any(PartyEntity.class))).thenReturn(party);

        PartyEntity result = partyController.updateParty("party123", party);

        assertEquals("party123", result.getId());
    }

    @Test
    public void testDeleteParty_Success() {
        doNothing().when(partyRepository).deleteById("party123");

        assertDoesNotThrow(() -> partyController.deleteParty("party123"));
    }

}