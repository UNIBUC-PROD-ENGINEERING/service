package ro.unibuc.triplea.domain.games.steam.service;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.domain.games.steam.repository.SteamGameRepository;

import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.hamcrest.Matchers.hasSize;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Tag("IT")
public class SteamGameServiceTestIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private SteamGameRepository steamGameRepository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(mockMvc);
        Assertions.assertNotNull(webApplicationContext);
    }

    @WithMockUser(username = "user", roles = "USER")
    @Test
    public void givenAuthorizedUserAndValidSteamId_whenGetGameByIdentifier_thenReturnsGame() throws Exception {
        int steamId = 12345;
        SteamGameResponse expectedGame = SteamGameResponse.builder()
                .gameSteamId(steamId)
                .gameName("Test Game")
                .build();

        when(steamGameRepository.findByGameSteamId(steamId)).thenReturn(Optional.of(expectedGame));

        mockMvc.perform(get("/api/v1/games/steam/" + steamId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameSteamId").value(steamId))
                .andExpect(jsonPath("$.gameName").value("Test Game"));
    }

    @WithMockUser(username = "user", roles = "USER")
    @Test
    public void givenAuthorizedUserAndValidGameName_whenGetGameByIdentifier_thenReturnsGame() throws Exception {
        String gameName = "Test Game";
        SteamGameResponse expectedGame = SteamGameResponse.builder()
                .gameSteamId(12345)
                .gameName(gameName)
                .build();

        when(steamGameRepository.findByGameName(gameName)).thenReturn(Optional.of(expectedGame));

        mockMvc.perform(get("/api/v1/games/steam/" + gameName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameSteamId").value(12345))
                .andExpect(jsonPath("$.gameName").value(gameName));
    }

    @WithMockUser(username = "user", roles = "USER")
    @Test
    public void givenAuthorizedUser_whenGetAllGames_thenReturnsGames() throws Exception {
        List<SteamGameResponse> expectedGames = new ArrayList<>();
        expectedGames.add(SteamGameResponse.builder().gameSteamId(1).gameName("Game 1").build());
        expectedGames.add(SteamGameResponse.builder().gameSteamId(2).gameName("Game 2").build());

        when(steamGameRepository.findGames(any())).thenReturn(expectedGames);

        mockMvc.perform(get("/api/v1/games/steam/game-list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].gameSteamId").value(1))
                .andExpect(jsonPath("$[0].gameName").value("Game 1"))
                .andExpect(jsonPath("$[1].gameSteamId").value(2))
                .andExpect(jsonPath("$[1].gameName").value("Game 2"));
    }

    @WithMockUser(username = "user", roles = "USER")
    @Test
    public void givenAuthorizedUserAndInvalidGameIdentifier_whenGetGameByIdentifier_thenReturnsGameNotFound() throws Exception {
        String identifier = "Non-existent Game";
        when(steamGameRepository.findByGameSteamId(anyInt())).thenReturn(Optional.empty());
        when(steamGameRepository.findByGameName(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/games/steam/" + identifier))
                .andExpect(status().isNotFound());
    }

}
