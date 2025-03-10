package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.dto.RobotDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.RobotService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RobotControllerTest {

    @Mock
    private RobotService robotService;

    @InjectMocks
    private RobotController robotController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(robotController).build();
    }

    @Test
    void testGetAllRobots() throws Exception {
        List<RobotDTO> robots = Arrays.asList(
                new RobotDTO("1", "active", "1", 10, "none"),
                new RobotDTO("2", "inactive", "2", 5, "battery low")
        );
        when(robotService.getAllRobots()).thenReturn(robots);

        mockMvc.perform(get("/robots"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].status").value("active"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].status").value("inactive"));
    }

    @Test
    void testGetRobotById_ExistingEntity() throws Exception {
        String id = "1";
        RobotDTO robot = new RobotDTO(id, "active", "1", 10, "none");
        when(robotService.getRobotById(id)).thenReturn(robot);

        mockMvc.perform(get("/robots/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.status").value("active"));
    }

    @Test
    void testCreateRobot() throws Exception {
        RobotDTO newRobot = new RobotDTO(null, "active", "1", 0, "none");
        RobotDTO createdRobot = new RobotDTO("1", "active", "1", 0, "none");
        when(robotService.createRobot(any(RobotDTO.class))).thenReturn(createdRobot);

        mockMvc.perform(post("/robots")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"active\",\"currentOrderId\":\"1\",\"completedOrders\":0,\"errors\":\"none\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.status").value("active"));
    }

    @Test
    void testUpdateRobotStatus_ExistingEntity() throws Exception {
        String id = "1";
        String status = "inactive";
        RobotDTO updatedRobot = new RobotDTO(id, status, "1", 10, "none");
        when(robotService.updateRobotStatus(eq(id), eq(status))).thenReturn(updatedRobot);

        mockMvc.perform(put("/robots/{id}/status", id)
                .param("status", status))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.status").value(status));
    }

    @Test
    void testDeleteRobot_ExistingEntity() throws Exception {
        String id = "1";
        doNothing().when(robotService).deleteRobot(id);

        mockMvc.perform(delete("/robots/{id}", id))
                .andExpect(status().isOk());

        verify(robotService, times(1)).deleteRobot(id);
    }
}
