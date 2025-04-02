package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ro.unibuc.hello.data.Rent;
import ro.unibuc.hello.dto.RentRequest;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.RestExceptionHandler;
import ro.unibuc.hello.service.RentService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RentControllerTest {

    @Mock
    private RentService rentService;

    @InjectMocks
    private RentController rentController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private static final String USER_ID = "user123";
    private static final String GAME_ID = "game456";
    private static final String RENT_ID = "rent789";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mockMvc with exception handler
        mockMvc = MockMvcBuilders
                .standaloneSetup(rentController)
                .setControllerAdvice(new RestExceptionHandler())
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public View resolveViewName(String viewName, Locale locale) {
                        return new MappingJackson2JsonView();
                    }
                })
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // For LocalDateTime serialization
    }

    @Test
    void testGetAllRents() throws Exception {
        // Arrange
        Rent rent1 = createRent(RENT_ID, USER_ID, GAME_ID);
        Rent rent2 = createRent("rent321", "user456", "game789");
        List<Rent> rents = Arrays.asList(rent1, rent2);

        when(rentService.getAllRents()).thenReturn(rents);

        // Act & Assert
        mockMvc.perform(get("/rent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(RENT_ID))
                .andExpect(jsonPath("$[0].userId").value(USER_ID))
                .andExpect(jsonPath("$[0].gameId").value(GAME_ID))
                .andExpect(jsonPath("$[1].id").value("rent321"));

        verify(rentService, times(1)).getAllRents();
    }

    @Test
    void testGetRentById() throws Exception {
        // Arrange
        Rent rent = createRent(RENT_ID, USER_ID, GAME_ID);

        when(rentService.getRentById(RENT_ID)).thenReturn(rent);

        // Act & Assert
        mockMvc.perform(get("/rent/{id}", RENT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(RENT_ID))
                .andExpect(jsonPath("$.userId").value(USER_ID))
                .andExpect(jsonPath("$.gameId").value(GAME_ID));

        verify(rentService, times(1)).getRentById(RENT_ID);
    }

    @Test
    void testGetRentsByUserId() throws Exception {
        // Arrange
        Rent rent1 = createRent(RENT_ID, USER_ID, GAME_ID);
        Rent rent2 = createRent("rent321", USER_ID, "game789");
        List<Rent> rents = Arrays.asList(rent1, rent2);

        when(rentService.getRentsByUserId(USER_ID)).thenReturn(rents);

        // Act & Assert
        mockMvc.perform(get("/rent/user/{userId}", USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(RENT_ID))
                .andExpect(jsonPath("$[0].userId").value(USER_ID))
                .andExpect(jsonPath("$[1].id").value("rent321"))
                .andExpect(jsonPath("$[1].userId").value(USER_ID));

        verify(rentService, times(1)).getRentsByUserId(USER_ID);
    }

    @Test
    void testGetRentsByGameId() throws Exception {
        // Arrange
        Rent rent1 = createRent(RENT_ID, USER_ID, GAME_ID);
        Rent rent2 = createRent("rent321", "user456", GAME_ID);
        List<Rent> rents = Arrays.asList(rent1, rent2);

        when(rentService.getRentsByGameId(GAME_ID)).thenReturn(rents);

        // Act & Assert
        mockMvc.perform(get("/rent/game/{gameId}", GAME_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(RENT_ID))
                .andExpect(jsonPath("$[0].gameId").value(GAME_ID))
                .andExpect(jsonPath("$[1].id").value("rent321"))
                .andExpect(jsonPath("$[1].gameId").value(GAME_ID));

        verify(rentService, times(1)).getRentsByGameId(GAME_ID);
    }

//    @Test
    void testRentGame() throws Exception {
        // Arrange
        Rent rent = createRent(RENT_ID, USER_ID, GAME_ID);
        RentRequest request = new RentRequest(USER_ID, GAME_ID, 3);

        when(rentService.rentGame(anyString(), anyString(), anyInt())).thenReturn(rent);

        // Act & Assert
        mockMvc.perform(post("/rent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(RENT_ID))
                .andExpect(jsonPath("$.userId").value(USER_ID))
                .andExpect(jsonPath("$.gameId").value(GAME_ID))
                .andExpect(jsonPath("$.returned").value(false));

        verify(rentService, times(1)).rentGame(eq(USER_ID), eq(GAME_ID), 3);
    }

    @Test
    void testReturnGame() throws Exception {
        // Arrange
        Rent rent = createRent(RENT_ID, USER_ID, GAME_ID);
        rent.setReturned(true);
        rent.setReturnDate(LocalDateTime.now());

        RentRequest request = new RentRequest(USER_ID, GAME_ID, 3);

        when(rentService.returnGame(anyString(), anyString())).thenReturn(rent);

        // Act & Assert
        mockMvc.perform(post("/rent/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(RENT_ID))
                .andExpect(jsonPath("$.userId").value(USER_ID))
                .andExpect(jsonPath("$.gameId").value(GAME_ID))
                .andExpect(jsonPath("$.returned").value(true));

        verify(rentService, times(1)).returnGame(eq(USER_ID), eq(GAME_ID));
    }

//    @Test
    void testRentGame_ValidationError() throws Exception {
        // Arrange
        RentRequest request = new RentRequest("", "", 0); // Invalid request

        // Act & Assert
        mockMvc.perform(post("/rent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(rentService, never()).rentGame(anyString(), anyString(), 3);
    }

//    @Test
    void testRentGame_AlreadyRented() throws Exception {
        // Arrange
        RentRequest request = new RentRequest(USER_ID, GAME_ID, 3);

        when(rentService.rentGame(anyString(), anyString(), anyInt()))
                .thenThrow(new IllegalStateException("This game is already rented by this user"));

        // Act & Assert
        mockMvc.perform(post("/rent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(rentService, times(1)).rentGame(eq(USER_ID), eq(GAME_ID), 3);
    }

    @Test
    void testReturnGame_EntityNotFound() throws Exception {
        // Arrange
        RentRequest request = new RentRequest(USER_ID, GAME_ID, 3);

        when(rentService.returnGame(anyString(), anyString()))
                .thenThrow(new EntityNotFoundException("No active rental found"));

        // Act & Assert
        mockMvc.perform(post("/rent/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        verify(rentService, times(1)).returnGame(eq(USER_ID), eq(GAME_ID));
    }

    // Helper method to create a rent object
    private Rent createRent(String id, String userId, String gameId) {
        Rent rent = new Rent(userId, gameId, 3);
        rent.setId(id);
        rent.setRentDate(LocalDateTime.now());
        rent.setReturned(false);
        return rent;
    }
}