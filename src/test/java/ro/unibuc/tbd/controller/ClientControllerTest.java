package ro.unibuc.tbd.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.tbd.model.Client;
import ro.unibuc.tbd.service.ClientService;

@SpringBootTest
class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private final String API_PATH = "/api/client/";

    private Client client;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
        objectMapper = new ObjectMapper();

        client = new Client();
        client.setId("gfdgdf4325s01583f06eff");
        client.setName("John");
        client.setEmail("john@gmail.com");
        client.setAddress("284 Garfield Ave. Hackensack, NJ 07601");
        client.setPhoneNumber("0762476343");
        client.setCart(Map.of("622ac0a71068101583f06eff", 1));
    }

    @Test
    void getClientById() throws Exception {
        // Arrange
        when(clientService.getClientById(any())).thenReturn(client);

        // Act
        MvcResult result = mockMvc.perform(get(API_PATH + client.getId()))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(client));
    }

    @Test
    void controller_PropagatesCascadedException() {
        // Arrange
        String clientId = "gjdfspoas34s01583f06eff";
        when(clientService.getClientById(any())).thenThrow(new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Client not found."));

        // Act
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> clientController.getClientById(clientId),
                "Expected getClientById() to throw a ResponseStatusException, but it didn't."
        );

        // Assert
        assertTrue(Objects.requireNonNull(exception.getMessage()).contains("Client not found."));
    }

    @Test
    void registerClient() throws Exception {
        // Arrange
        when(clientService.createClient(any())).thenReturn(client);

        // Act
        MvcResult result = mockMvc.perform(post(API_PATH)
                        .content(objectMapper.writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(client));
    }

    @Test
    void addToCart() throws Exception {
        // Arrange
        when(clientService.addToCart(any(), any())).thenReturn(client);

        // Act
        MvcResult result = mockMvc.perform(post(API_PATH + client.getId() + "/cart")
                        .content(objectMapper.writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(client));
    }

    @Test
    void removeFromCart() throws Exception {
        // Arrange
        when(clientService.removeFromCart(any(), any())).thenReturn(client);

        // Act
        MvcResult result = mockMvc.perform(delete(API_PATH + client.getId() + "/cart")
                        .content(objectMapper.writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(client));
    }

    @Test
    void placeOrder() throws Exception {
        // Arrange
        when(clientService.placeOrder(any())).thenReturn(client);

        // Act
        MvcResult result = mockMvc.perform(get(API_PATH + client.getId() + "/place-order")
                        .content(objectMapper.writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(client));
    }

    @Test
    void updateClient() throws Exception {
        // Arrange
        when(clientService.updateClient(any(), any())).thenReturn(client);

        // Act
        MvcResult result = mockMvc.perform(patch(API_PATH + client.getId())
                        .content(objectMapper.writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(client));
    }

    @Test
    void deleteClient() throws Exception {
        // Arrange
        when(clientService.deleteClientById(any())).thenReturn(client);

        // Act
        MvcResult result = mockMvc.perform(delete(API_PATH + client.getId())
                        .content(objectMapper.writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(client));
    }
}