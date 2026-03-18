package ro.unibuc.prodeng.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.prodeng.model.RestaurantEntity;
import ro.unibuc.prodeng.repository.RestaurantRepository;
import ro.unibuc.prodeng.response.RestaurantResponse;
import java.util.List;
import java.util.Arrays;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    void testCreate_withValidRating_savesSuccessfully() {
        // Arrange
        RestaurantResponse request = new RestaurantResponse(null, "Test", "Adresa", "Pizza", 4.5);
        RestaurantEntity savedEntity = new RestaurantEntity("1", "Test", "Adresa", "Pizza", 4.5);
        when(restaurantRepository.save(any())).thenReturn(savedEntity);

        // Act
        RestaurantResponse result = restaurantService.create(request);

        // Assert
        assertNotNull(result.getId());
        assertEquals(4.5, result.getRating());
        verify(restaurantRepository, times(1)).save(any());
    }

    @Test
    void testCreate_withInvalidRating_throwsException() {
        // Arrange
        RestaurantResponse request = new RestaurantResponse(null, "Bad", "Addr", "Cuisine", 10.0);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> restaurantService.create(request));
        verify(restaurantRepository, never()).save(any());
    }
    @Test
void testGetAll_returnsList() {
    when(restaurantRepository.findAll()).thenReturn(List.of(new RestaurantEntity()));
    List<RestaurantResponse> result = restaurantService.getAll();
    assertEquals(1, result.size());
}

@Test
void testGetById_notFound_throwsException() {
    when(restaurantRepository.findById("999")).thenReturn(Optional.empty());
    assertThrows(ResponseStatusException.class, () -> restaurantService.getById("999"));
}

@Test
void testDelete_existingId_deletesSuccessfully() {
    when(restaurantRepository.existsById("1")).thenReturn(true);
    assertDoesNotThrow(() -> restaurantService.delete("1"));
    verify(restaurantRepository).deleteById("1");
}

@Test
void testDelete_nonExistingId_throwsException() {
    when(restaurantRepository.existsById("999")).thenReturn(false);
    assertThrows(ResponseStatusException.class, () -> restaurantService.delete("999"));
}
}