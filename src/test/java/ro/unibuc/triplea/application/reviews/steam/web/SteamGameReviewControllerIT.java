package ro.unibuc.triplea.application.reviews.steam.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@Tag("IT")
class SteamGameReviewControllerIT {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

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
    void givenUnauthorizedUser_whenGetReviewsBySteamId_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/reviews/steam/game-id/50000"))
                .andExpect(status().isOk());
    }
}