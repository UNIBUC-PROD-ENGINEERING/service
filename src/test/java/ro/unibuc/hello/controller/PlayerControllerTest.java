package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import ro.unibuc.hello.data.PlayerEntity;
import ro.unibuc.hello.data.PlayerRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.PlayerService;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class PlayerControllerTest {
        @Mock
        private PlayerService playerService;

        @InjectMocks
        private PlayerController playerController;

        private MockMvc mockMvc;

        private ObjectMapper objectMapper;

        @BeforeEach
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
                objectMapper = new ObjectMapper();
        }

        @Test
        void test_GetPlayer() throws Exception {
                PlayerEntity playerEntity = new PlayerEntity("1", "LeBron James", "Los Angeles Lakers", 25.0, 7.8, 8.0);

                when(playerService.getPlayer("LeBron James")).thenReturn(playerEntity.toString());
                MvcResult result = mockMvc.perform(get("/player?name=LeBron James")
                                .content(objectMapper.writeValueAsString(playerEntity.toString()))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andReturn();

                Assertions.assertEquals(playerEntity.toString(), result.getResponse().getContentAsString());

        }

        @Test
        void test_TeamForPlayer() throws Exception {
                PlayerEntity playerEntity = new PlayerEntity("1", "LeBron James", "Los Angeles Lakers", 25.0, 7.8, 8.0);

                when(playerService.getPlayerTeam("LeBron James")).thenReturn(playerEntity.getTeam());
                MvcResult result = mockMvc.perform(get("/player/getTeamForPlayer?name=LeBron James")
                                .content(objectMapper.writeValueAsString(playerEntity.toString()))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andReturn();

                Assertions.assertEquals(playerEntity.getTeam(), result.getResponse().getContentAsString());

        }

        @Test
        void test_PostPlayer() throws Exception {
                // Create a PlayerEntity object
                PlayerEntity player = new PlayerEntity("100", "Dragos", "Los Angeles Lakers", 25.0, 7.8, 8.0);

                // Mock the behavior of playerService.createPlayer()
                when(playerService.createPlayer(any(PlayerEntity.class))).thenReturn(player);

                // Perform the POST request and expect OK status
                MvcResult result = mockMvc.perform(post("/player")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                                "{\"id\":\"100\",\"name\":\"Dragos\",\"team\":\"Los Angeles Lakers\",\"pointsPerGame\":25.0,\"reboundsPerGame\":7.8,\"assistsPerGame\":8.0}"))
                                .andExpect(status().isOk())
                                .andReturn();

                // Convert the JSON response content back to a PlayerEntity object
                ObjectMapper objectMapper = new ObjectMapper();
                PlayerEntity responsePlayer = objectMapper.readValue(result.getResponse().getContentAsString(),
                                PlayerEntity.class);
                // Compare the responsePlayer object with the expected player object
                Assertions.assertEquals(player, responsePlayer);
        }

        @Test
        void test_UpdatePlayer() throws Exception {
                PlayerEntity player = new PlayerEntity("1", "LeBron James", "Los Angeles Lakers", 255.0, 7.8, 8.0);
                when(playerService.updatePlayer(anyString(), any())).thenReturn(player);
                MvcResult result = mockMvc.perform(put("/player/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                                "{\"id\":\"1\",\"name\":\"LeBron James\",\"team\":\"Los Angeles Lakers\",\"pointsPerGame\":255.0,\"reboundsPerGame\":7.8,\"assistsPerGame\":8.0}"))
                                .andExpect(status().isOk())
                                .andReturn();
                // Convert the JSON response content back to a PlayerEntity object
                ObjectMapper objectMapper = new ObjectMapper();
                PlayerEntity responsePlayer = objectMapper.readValue(result.getResponse().getContentAsString(),
                                PlayerEntity.class);
                // Compare the responsePlayer object with the expected player object
                Assertions.assertEquals(player, responsePlayer);
        }

        @Test
        void test_DeletePlayerByName() throws Exception {
                when(playerService.deleteByName(anyString())).thenReturn("Player deleted succesfully");

                MvcResult result = mockMvc.perform(delete("/player")
                                .param("name", "LeBron James"))
                                .andExpect(status().isOk())
                                .andReturn();
                Assertions.assertEquals(playerService.deleteByName(anyString()),
                                result.getResponse().getContentAsString());
        }

        @Test
        void test_EntityNotFoundException() throws Exception {
                // Mock the behavior of playerService.getPlayer()
                when(playerService.getPlayer(anyString())).thenThrow(new EntityNotFoundException("Player not found"));

                // Perform the GET request and expect the EntityNotFoundException
                Exception exception = assertThrows(NestedServletException.class, () -> {
                        mockMvc.perform(get("/player?name=NonExistentPlayer")
                                        .contentType(MediaType.APPLICATION_JSON))
                                        .andExpect(status().isNotFound());
                });

                assertTrue(exception.getMessage().contains("Player not found"));
        }

        @Test
        void test_DeleteNonExistingPlayerByName() throws Exception {
                when(playerService.deleteByName(anyString())).thenReturn("Player not found");

                AssertionError exception = assertThrows(AssertionError.class, () -> {
                        mockMvc.perform(get("/player?name=NonExistentPlayer")
                                        .contentType(MediaType.APPLICATION_JSON))
                                        .andExpect(status().isNotFound());
                });
                assertFalse(exception.getMessage().contains("Player not found"));
        }

        @Test
        void test_GetAllPlayers() throws Exception {
                // Create a list of PlayerEntity objects
                List<PlayerEntity> players = new ArrayList<>();
                players.add(new PlayerEntity("100", "Dragos", "Los Angeles Lakers", 25.0, 7.8, 8.0));
                players.add(new PlayerEntity("101", "Ion", "Chicago Bulls", 20.0, 5.8, 6.0));

                // Mock the behavior of playerService.getAllPlayers()
                when(playerService.getAllPlayers()).thenReturn(players);

                // Perform the GET request and expect OK status
                MvcResult result = mockMvc.perform(get("/player/getAllPlayers")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andReturn();

                Assertions.assertEquals(objectMapper.writeValueAsString(players),
                                result.getResponse().getContentAsString());
        }

}
