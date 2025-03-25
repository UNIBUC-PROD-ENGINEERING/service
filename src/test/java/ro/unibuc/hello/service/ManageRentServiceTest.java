package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.unibuc.hello.data.Rent;
import ro.unibuc.hello.data.RentRepository;
import ro.unibuc.hello.dto.LateRent;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManageRentServiceTest {

    private RentRepository rentRepository;
    private ManageRentService manageRentService;

    @BeforeEach
    void setUp() {
        rentRepository = mock(RentRepository.class);
        manageRentService = new ManageRentService(rentRepository);
    }

    @Test
    void testGetAllActiveRents() {
        Rent rent1 = new Rent("user1", "game1", 7);
        Rent rent2 = new Rent("user2", "game2", 5);
        rent2.setReturned(true);

        when(rentRepository.findAll()).thenReturn(Arrays.asList(rent1, rent2));

        List<Rent> result = manageRentService.getAllActiveRents();

        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getUserId());
    }

    @Test
    void testGetLateRents() {
        Rent lateRent = new Rent("user1", "game1", 7);
        lateRent.setRentDate(LocalDateTime.now().minusDays(10));

        Rent onTimeRent = new Rent("user2", "game2", 5);
        onTimeRent.setRentDate(LocalDateTime.now().minusDays(2));

        Rent returnedRent = new Rent("user3", "game3", 3);
        returnedRent.setRentDate(LocalDateTime.now().minusDays(10));
        returnedRent.setReturned(true);

        when(rentRepository.findAll()).thenReturn(Arrays.asList(lateRent, onTimeRent, returnedRent));

        List<LateRent> result = manageRentService.getLateRents();

        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getUserId());
    }
}