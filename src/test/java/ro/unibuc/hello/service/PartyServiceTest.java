package test.java.ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ro.unibuc.hello.data.FoodEntity;
import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.data.TaskEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.repositories.PartyRepository;
import ro.unibuc.hello.repositories.TaskRepository;
import ro.unibuc.hello.repositories.UserRepository;
import ro.unibuc.hello.repositories.FoodRepository;
import ro.unibuc.hello.repositories.LocationRepository;
import ro.unibuc.hello.service.PartyService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class PartyServiceTest {

    @Mock
    private PartyRepository partyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private PartyService partyService;

    private PartyEntity party;
    private UserEntity user;
    private TaskEntity task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock data
        user = new UserEntity("user1", "john.doe@example.com", "password123");
        
        // Inițializare petrecere cu 50 de puncte
        party = new PartyEntity("Party Name", "2025-12-31", "user1");
        party.setPartyPoints(50);  // Asigură-te că petrecerea începe cu 50 de puncte

        // Inițializare task cu 100 de puncte
        task = new TaskEntity("task1", "Description of task1", 100, "party1", "user1");
        task.setCompleted(true);  // Marchează taskul ca fiind completat

        // Definește comportamentul repository-urilor
        when(partyRepository.findById("party1")).thenReturn(Optional.of(party));
        when(userRepository.findById("user1")).thenReturn(Optional.of(user));
        when(taskRepository.findById("task1")).thenReturn(Optional.of(task));
        when(partyRepository.save(party)).thenReturn(party);
// Mock pentru FoodRepository
    when(foodRepository.findById("food1")).thenReturn(Optional.of(new FoodEntity("Food Name", 10.0, 4.5, 10)));

    }

    @Test
    void test_addUserToParty() {
        PartyEntity updatedParty = partyService.addUserToParty("party1", "user2");

        // Verifică că utilizatorul a fost adăugat la petrecere
        assertNotNull(updatedParty);
        assertTrue(updatedParty.getUserIds().contains("user2"));
    }

    @Test
    void test_updatePartyPointsAfterTaskCompletion() {
        // Să presupunem că task-ul a fost completat
        task.setCompleted(true);

        PartyEntity updatedParty = partyService.updatePartyPointsAfterTaskCompletion("party1", "user1", "task1");

        // Verifică că punctele petrecerii au fost actualizate
        assertNotNull(updatedParty);
        assertEquals(150, updatedParty.getPartyPoints());
    }

    
    @Test
    void test_getPartiesForUser() {
        when(partyRepository.findByUserIdsContaining("user1")).thenReturn(Arrays.asList(party));

        List<PartyEntity> parties = partyService.getPartiesForUser("user1");

        // Verifică că petrecerea utilizatorului a fost găsită
        assertNotNull(parties);
        assertFalse(parties.isEmpty());
        assertEquals("Party Name", parties.get(0).getName());
    }

}