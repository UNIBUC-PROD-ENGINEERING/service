package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.ApartmentEntity;
import ro.unibuc.hello.exception.InvalidInputException;
import ro.unibuc.hello.service.ApartmentService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ApartmentControllerTest {

    @Mock
    private ApartmentService apartmentService;

    @InjectMocks
    private ApartmentController apartmentController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private ApartmentEntity luxuryApartment;
    private ApartmentEntity modernFlat;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(apartmentController).build();
        objectMapper = new ObjectMapper();

        // Date de test
        luxuryApartment = new ApartmentEntity();
        luxuryApartment.setId("67e0582d1bf7c4337e952c36");
        luxuryApartment.setTitle("Luxury Apartment");
        luxuryApartment.setLocation("București");
        luxuryApartment.setPricePerNight(250.0);
        luxuryApartment.setNumberOfRooms(3);
        luxuryApartment.setNumberOfBathrooms(2);
        luxuryApartment.setAmenities(Arrays.asList("Wi-Fi", "TV", "balcon", "aer condiționat"));
        luxuryApartment.setSquareMeters(90.0);
        luxuryApartment.setSmokingAllowed(false);
        luxuryApartment.setPetFriendly(true);
        luxuryApartment.setUserId("user1");

        modernFlat = new ApartmentEntity();
        modernFlat.setId("67e0582d1bf7c4337e952c37");
        modernFlat.setTitle("Modern Flat");
        modernFlat.setLocation("Cluj-Napoca");
        modernFlat.setPricePerNight(180.0);
        modernFlat.setNumberOfRooms(2);
        modernFlat.setNumberOfBathrooms(1);
        modernFlat.setAmenities(Arrays.asList("Wi-Fi", "TV", "mașină de spălat"));
        modernFlat.setSquareMeters(65.5);
        modernFlat.setSmokingAllowed(true);
        modernFlat.setPetFriendly(false);
        modernFlat.setUserId("user1");
    }

    @Test
    void getAllApartments() throws Exception {
        List<ApartmentEntity> apartments = Arrays.asList(luxuryApartment, modernFlat);
        when(apartmentService.getAllApartments()).thenReturn(apartments);

        mockMvc.perform(get("/apartments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("67e0582d1bf7c4337e952c36"))
                .andExpect(jsonPath("$[0].title").value("Luxury Apartment"))
                .andExpect(jsonPath("$[1].id").value("67e0582d1bf7c4337e952c37"))
                .andExpect(jsonPath("$[1].title").value("Modern Flat"));
    }

    @Test
    void getApartmentById_Success() throws Exception {
        when(apartmentService.getApartmentById("67e0582d1bf7c4337e952c36")).thenReturn(Optional.of(luxuryApartment));

        mockMvc.perform(get("/apartments/67e0582d1bf7c4337e952c36"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("67e0582d1bf7c4337e952c36"))
                .andExpect(jsonPath("$.title").value("Luxury Apartment"));
    }

    @Test
    void getApartmentById_NotFound() throws Exception {
        when(apartmentService.getApartmentById("nonexistent")).thenReturn(Optional.empty());

        mockMvc.perform(get("/apartments/nonexistent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void createApartment_Success() throws Exception {
        when(apartmentService.createApartment(any(ApartmentEntity.class))).thenReturn(luxuryApartment);

        mockMvc.perform(post("/apartments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(luxuryApartment)))
                .andExpect(status().isOk())
                .andExpect(content().string("Apartment successfully created!"));
    }

    @Test
    void createApartment_UserNotFound() throws Exception {
        when(apartmentService.createApartment(any(ApartmentEntity.class)))
                .thenThrow(new IllegalArgumentException("User with ID user1 does not exist."));

        mockMvc.perform(post("/apartments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(luxuryApartment)))
                .andExpect(status().isOk())
                .andExpect(content().string("User with ID user1 does not exist."));
    }

    @Test
    void deleteApartment() throws Exception {
        doNothing().when(apartmentService).deleteApartment("67e0582d1bf7c4337e952c36");

        mockMvc.perform(delete("/apartments/67e0582d1bf7c4337e952c36"))
                .andExpect(status().isOk());

        verify(apartmentService, times(1)).deleteApartment("67e0582d1bf7c4337e952c36");
    }

    @Test
    void getAvailableApartments() throws Exception {
        List<ApartmentEntity> availableApartments = Collections.singletonList(modernFlat);
        when(apartmentService.findAvailableApartments(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(availableApartments);

        mockMvc.perform(get("/apartments/available")
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-01-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("67e0582d1bf7c4337e952c37"))
                .andExpect(jsonPath("$[0].title").value("Modern Flat"));
    }

    @Test
    void isApartmentAvailable_Available() throws Exception {
        when(apartmentService.isApartmentAvailable(eq("67e0582d1bf7c4337e952c36"), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(true);

        mockMvc.perform(get("/apartments/67e0582d1bf7c4337e952c36/available")
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-01-05"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void isApartmentAvailable_NotAvailable() throws Exception {
        when(apartmentService.isApartmentAvailable(eq("67e0582d1bf7c4337e952c36"), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(false);

        mockMvc.perform(get("/apartments/67e0582d1bf7c4337e952c36/available")
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-01-05"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void getPetFriendlyApartments() throws Exception {
        List<ApartmentEntity> petFriendly = Collections.singletonList(luxuryApartment);
        when(apartmentService.getPetFriendlyApartments()).thenReturn(petFriendly);

        mockMvc.perform(get("/apartments/pet-friendly"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("67e0582d1bf7c4337e952c36"))
                .andExpect(jsonPath("$[0].petFriendly").value(true));
    }

    @Test
    void getApartmentsByNumberOfRooms_Success() throws Exception {
        List<ApartmentEntity> rooms = Collections.singletonList(modernFlat);
        when(apartmentService.getApartmentsByNumberOfRooms(2)).thenReturn(rooms);

        mockMvc.perform(get("/apartments/rooms/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("67e0582d1bf7c4337e952c37"))
                .andExpect(jsonPath("$[0].numberOfRooms").value(2));
    }

    @Test
    void getApartmentsByNumberOfRooms_InvalidInput() throws Exception {
        when(apartmentService.getApartmentsByNumberOfRooms(0))
                .thenThrow(new InvalidInputException("Number of rooms must be greater than zero"));

        mockMvc.perform(get("/apartments/rooms/0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Number of rooms must be greater than zero"));
    }

    @Test
    void getApartmentsByNumberOfBathrooms_Success() throws Exception {
        List<ApartmentEntity> bathrooms = Collections.singletonList(luxuryApartment);
        when(apartmentService.getApartmentsByNumberOfBathrooms(2)).thenReturn(bathrooms);

        mockMvc.perform(get("/apartments/bathrooms/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("67e0582d1bf7c4337e952c36"))
                .andExpect(jsonPath("$[0].numberOfBathrooms").value(2));
    }

    @Test
    void getApartmentsByMinimumSquareMeters_Success() throws Exception {
        List<ApartmentEntity> squareMeters = Collections.singletonList(luxuryApartment);
        when(apartmentService.getApartmentsByMinimumSquareMeters(80.0)).thenReturn(squareMeters);

        mockMvc.perform(get("/apartments/square-meters")
                        .param("minSquareMeters", "80.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("67e0582d1bf7c4337e952c36"))
                .andExpect(jsonPath("$[0].squareMeters").value(90.0));
    }

    @Test
    void getApartmentsByMaxPrice_Success() throws Exception {
        List<ApartmentEntity> prices = Collections.singletonList(modernFlat);
        when(apartmentService.getApartmentsByMaxPrice(200.0)).thenReturn(prices);

        mockMvc.perform(get("/apartments/price")
                        .param("maxPrice", "200.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("67e0582d1bf7c4337e952c37"))
                .andExpect(jsonPath("$[0].pricePerNight").value(180.0));
    }

    @Test
    void getApartmentsByAmenity_Success() throws Exception {
        List<ApartmentEntity> amenities = Arrays.asList(luxuryApartment, modernFlat);
        when(apartmentService.getApartmentsByAmenity("Wi-Fi")).thenReturn(amenities);

        mockMvc.perform(get("/apartments/amenities")
                        .param("amenity", "Wi-Fi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("67e0582d1bf7c4337e952c36"))
                .andExpect(jsonPath("$[1].id").value("67e0582d1bf7c4337e952c37"));
    }

    @Test
    void getApartmentsByAmenity_InvalidInput() throws Exception {
        when(apartmentService.getApartmentsByAmenity(""))
                .thenThrow(new InvalidInputException("Amenity must not be empty"));

        mockMvc.perform(get("/apartments/amenities")
                        .param("amenity", ""))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Amenity must not be empty"));
    }

    @Test
    void getApartmentsBySmokingAllowed_Success() throws Exception {
        List<ApartmentEntity> smoking = Collections.singletonList(modernFlat);
        when(apartmentService.getApartmentsBySmokingAllowed(true)).thenReturn(smoking);

        mockMvc.perform(get("/apartments/smoking")
                        .param("smokingAllowed", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("67e0582d1bf7c4337e952c37"))
                .andExpect(jsonPath("$[0].smokingAllowed").value(true));
    }

    @Test
    void getApartmentsByLocation_Success() throws Exception {
        List<ApartmentEntity> locations = Collections.singletonList(luxuryApartment);
        when(apartmentService.getApartmentsByLocation("București")).thenReturn(locations);

        mockMvc.perform(get("/apartments/location")
                        .param("location", "București"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("67e0582d1bf7c4337e952c36"))
                .andExpect(jsonPath("$[0].location").value("București"));
    }

    @Test
    void getApartmentsByLocation_InvalidInput() throws Exception {
        when(apartmentService.getApartmentsByLocation(""))
                .thenThrow(new InvalidInputException("Location must not be empty"));

        mockMvc.perform(get("/apartments/location")
                        .param("location", ""))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Location must not be empty"));
    }

    @Test
    void getApartmentsByPriceAndSquareMeters_Success() throws Exception {
        List<ApartmentEntity> filtered = Collections.singletonList(modernFlat);
        when(apartmentService.getApartmentsByPriceAndSquareMeters(150.0, 200.0, 60.0, 70.0))
                .thenReturn(filtered);

        mockMvc.perform(get("/apartments/filter")
                        .param("minPrice", "150.0")
                        .param("maxPrice", "200.0")
                        .param("minSquareMeters", "60.0")
                        .param("maxSquareMeters", "70.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("67e0582d1bf7c4337e952c37"))
                .andExpect(jsonPath("$[0].pricePerNight").value(180.0))
                .andExpect(jsonPath("$[0].squareMeters").value(65.5));
    }

    @Test
    void getApartmentsByPriceAndSquareMeters_InvalidInput() throws Exception {
        when(apartmentService.getApartmentsByPriceAndSquareMeters(200.0, 100.0, 50.0, 80.0))
                .thenThrow(new InvalidInputException("Minimum price cannot be greater than maximum price"));

        mockMvc.perform(get("/apartments/filter")
                        .param("minPrice", "200.0")
                        .param("maxPrice", "100.0")
                        .param("minSquareMeters", "50.0")
                        .param("maxSquareMeters", "80.0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Minimum price cannot be greater than maximum price"));
    }

    @Test
    void searchApartments_Success() throws Exception {
        List<ApartmentEntity> filtered = Collections.singletonList(modernFlat);
        when(apartmentService.searchApartments(null, 200.0, null, null, 2, null, null, true, "Wi-Fi", null, null))
                .thenReturn(filtered);

        mockMvc.perform(get("/apartments/search")
                        .param("maxPrice", "200.0")
                        .param("numberOfRooms", "2")
                        .param("smokingAllowed", "true")
                        .param("amenity", "Wi-Fi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("67e0582d1bf7c4337e952c37"))
                .andExpect(jsonPath("$[0].pricePerNight").value(180.0))
                .andExpect(jsonPath("$[0].numberOfRooms").value(2));
    }

    @Test
    void searchApartments_InvalidRating() throws Exception {
        when(apartmentService.searchApartments(null, null, null, null, null, null, null, null, null, null, 6.0))
                .thenThrow(new InvalidInputException("Minimum average rating must be between 1 and 5"));

        mockMvc.perform(get("/apartments/search")
                        .param("minAverageRating", "6.0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Minimum average rating must be between 1 and 5"));
    }

    @Test
    void getApartmentsByMinAverageRating_Success() throws Exception {
        List<ApartmentEntity> rated = Collections.singletonList(luxuryApartment);
        when(apartmentService.getApartmentsByMinAverageRating(4.0)).thenReturn(rated);

        mockMvc.perform(get("/apartments/by-rating")
                        .param("minAverageRating", "4.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("67e0582d1bf7c4337e952c36"))
                .andExpect(jsonPath("$[0].title").value("Luxury Apartment"));
    }

    @Test
    void getApartmentsByMinAverageRating_InvalidInput() throws Exception {
        when(apartmentService.getApartmentsByMinAverageRating(6.0))
                .thenThrow(new InvalidInputException("Minimum average rating must be between 1 and 5"));

        mockMvc.perform(get("/apartments/by-rating")
                        .param("minAverageRating", "6.0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Minimum average rating must be between 1 and 5"));
    }
}