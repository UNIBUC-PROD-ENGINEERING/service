package ro.unibuc.hello.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.dto.Location;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.LocationService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class LocationControllerTest {

    @Mock
    private LocationService locationSerivce;

    @InjectMocks
    private LocationController locationController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
        objectMapper = new ObjectMapper();
    }
    @Test
    void test_getLocationByAddress() throws Exception{
        // Arrange
        String address = "Bulevardul General Paul Teodorescu 4, București 061344";
        when(locationController.getLocationByAddress(any())).thenThrow(new EntityNotFoundException(address));

        // Act
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> locationController.getLocationByAddress(address));

        // Assert
        assertTrue(exception.getMessage().contains(address));

    }
}
