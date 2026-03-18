package ro.unibuc.prodeng.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.prodeng.response.RestaurantResponse;
import ro.unibuc.prodeng.service.RestaurantService;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper(); // Necesar pentru a transforma obiecte in JSON

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
    }

    @Test
    void testGetAll_returnsList() throws Exception {
        // ARRANGE
        when(restaurantService.getAll()).thenReturn(Arrays.asList(
            new RestaurantResponse("1", "Pizza Hut", "Mall", "Pizza", 4.0)
        ));

        // ACT & ASSERT
        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Pizza Hut")));
    }

    @Test
    void testCreate_returnsCreated() throws Exception {
        // ARRANGE
        RestaurantResponse request = new RestaurantResponse(null, " KFC", "Centru", "Pui", 4.2);
        when(restaurantService.create(any())).thenReturn(new RestaurantResponse("2", "KFC", "Centru", "Pui", 4.2));

        // ACT & ASSERT
        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))) // Transforma obiectul in string JSON
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("2")))
                .andExpect(jsonPath("$.name", is("KFC")));
    }
    @Test
void testGetById_returnsOk() throws Exception {
    when(restaurantService.getById("1")).thenReturn(new RestaurantResponse("1", "Nume", "Adresa", "Cuisine", 4.0));
    mockMvc.perform(get("/api/restaurants/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("Nume")));
}

@Test
void testDelete_returnsNoContent() throws Exception {
    mockMvc.perform(delete("/api/restaurants/1"))
            .andExpect(status().isNoContent());
    verify(restaurantService).delete("1");
}
}