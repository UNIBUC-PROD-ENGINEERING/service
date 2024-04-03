package ro.unibuc.triplea.application.favorites.steam.web;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ro.unibuc.triplea.application.favorites.steam.dto.request.SteamGameFavoriteRequest;
import ro.unibuc.triplea.application.favorites.steam.dto.response.SteamGameFavoriteResponse;
import ro.unibuc.triplea.domain.favorites.steam.service.SteamGameFavoriteService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Tag("IT")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SteamGameFavoriteControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // Mocking the service
    private SteamGameFavoriteService steamGameFavoriteService;

    @Test
    public void testAddFavorite() throws Exception {
        // Mocking the service method to return a fixed response
        SteamGameFavoriteResponse expectedResponse = new SteamGameFavoriteResponse("testUser", 123, "Test Game");
        when(steamGameFavoriteService.addFavorite(any(SteamGameFavoriteRequest.class), any(UserDetails.class)))
                .thenReturn(Optional.of(expectedResponse));

        // Making a request to the controller
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/favorites/steam/add")
                        .content("{\"gameSteamId\": 123}")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("testUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gameSteamId").value(123))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gameName").value("Test Game"));
    }
}
