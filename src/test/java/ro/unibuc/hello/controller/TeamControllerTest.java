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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.data.PlayerEntity;
import ro.unibuc.hello.data.TeamEntity;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.PlayerService;
import ro.unibuc.hello.service.TeamService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
public class TeamControllerTest {
    @Mock
    private TeamService teamService;

    @InjectMocks
    private TeamController teamController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_CreateTeam() throws Exception {
        // Create a TeamEntity object
        TeamEntity team = new TeamEntity("100", "PHXGlati", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 1967,
                "Frank Vogel");

        // Mock the behavior of TeamService.createTeam()
        when(teamService.create(any(TeamEntity.class))).thenReturn(team);
        // Perform the POST request and expect OK status
        MvcResult result = mockMvc.perform(post("/team/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"id\":\"100\",\"name\":\"PHXGlati\",\"players\":[1,2,3,4,5,6,7,8,9,10],\"yearFounded\":0,\"coach\":\"Frank Vogel\",\"teamInfo\":\"Team PHXGlati was founded in 0. The coach is Frank Vogel.\"}"))
                .andExpect(status().isOk())
                .andReturn();

        // Convert the JSON response content back to a TeamEntity object
        ObjectMapper objectMapper = new ObjectMapper();
        TeamEntity responseTeam = objectMapper.readValue(result.getResponse().getContentAsString(),
                TeamEntity.class);
        // Compare the responseTeam object with the expected team object
        Assertions.assertEquals(team, responseTeam);
    }

    @Test
    void test_GetTeam() throws Exception {
        TeamEntity teamEntity = new TeamEntity("1", "Los Angeles Lakers", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                1947, "Frank Vogel");

        when(teamService.getTeam("Los Angeles Lakers")).thenReturn(teamEntity.toString());
        MvcResult result = mockMvc.perform(get("/team/getTeam?name=Los Angeles Lakers")
                .content(objectMapper.writeValueAsString(teamEntity.toString()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(teamEntity.toString(), result.getResponse().getContentAsString());
    }

    @Test
    void test_UpdateTeam() throws Exception {
        TeamEntity updatedTeam = new TeamEntity();
        updatedTeam.setName("Golden State Warriors");
        updatedTeam.setPlayers(Arrays.asList(11, 12, 13));
        updatedTeam.setYearFounded(1946);
        updatedTeam.setCoach("Steve Kerr");

        when(teamService.updateTeam("1", updatedTeam)).thenReturn(updatedTeam);
        String updatedTeamJson = objectMapper.writeValueAsString(updatedTeam);

        mockMvc.perform(MockMvcRequestBuilders.put("/team/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedTeamJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(updatedTeamJson));
    }

    @Test
    void test_DeleteTeamByName() throws Exception {
        when(teamService.deleteByName(anyString())).thenReturn("Team deleted succesfully");

        MvcResult result = mockMvc.perform(delete("/team/deleteTeamByName")
                .param("name", "Brooklyn Nets"))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(teamService.deleteByName(anyString()), result.getResponse().getContentAsString());
    }

    @Test
        void test_EntityNotFoundException() throws Exception {
                // Mock the behavior of playerService.getPlayer()
                when(teamService.getTeam(anyString())).thenThrow(new EntityNotFoundException("Team not found"));

                // Perform the GET request and expect the EntityNotFoundException
                Exception exception = assertThrows(NestedServletException.class, () -> {
                        mockMvc.perform(get("/team/getTeam?name=NonExistentTeam")
                                        .contentType(MediaType.APPLICATION_JSON))
                                        .andExpect(status().isNotFound());
                });

                assertTrue(exception.getMessage().contains("Team not found"));
        }
         @Test
        void test_DeleteNonExistingTeamByName() throws Exception {
                when(teamService.deleteByName(anyString())).thenReturn("Team not found");

                AssertionError exception = assertThrows(AssertionError.class, () -> {
                        mockMvc.perform(get("/team/deleteTeamByName?name=NonExistentTeam")
                                        .contentType(MediaType.APPLICATION_JSON))
                                        .andExpect(status().isNotFound());
                });
                assertFalse(exception.getMessage().contains("Team not found"));
        }

}
