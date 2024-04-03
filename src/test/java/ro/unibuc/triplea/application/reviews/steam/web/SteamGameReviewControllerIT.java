package ro.unibuc.triplea.application.reviews.steam.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.domain.games.steam.repository.SteamGameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import java.util.Optional;

@SpringBootTest
@Tag("IT")
class SteamGameReviewControllerIT {
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

    @Test
    void givenUnauthorizedUser_whenGetReviewsBySteamId_thenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/reviews/steam/game-id/123"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "user", roles = "USER")
    @Test
    void givenAuthorizedUserAndValidGameIdentifier_whenGetReviewsBySteamId_thenOk() throws Exception {
        SteamGameResponse expectedGame = SteamGameResponse.builder()
                .gameSteamId(50000)
                .gameName("Test Game")
                .build();

        when(steamGameRepository.findByGameSteamId(50000)).thenReturn(Optional.of(expectedGame));

        mockMvc.perform(get("/api/v1/reviews/steam/game-id/50000"))
                .andExpect(status().isOk());
    }
}