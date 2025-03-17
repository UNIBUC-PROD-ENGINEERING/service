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
        // Arrange
        List<RobotDTO> robots = Arrays.asList(
                new RobotDTO("1", "active", "order1", 10, "none"),
                new RobotDTO("2", "inactive", null, 5, "battery low")
        );
        when(robotService.getAllRobots()).thenReturn(robots);

        // Act & Assert
        mockMvc.perform(get("/robots"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].status").value("active"))
                .andExpect(jsonPath("$[0].currentOrderId").value("order1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].status").value("inactive"))
                .andExpect(jsonPath("$[1].currentOrderId").doesNotExist());
    }

    @Test
    void testGetRobotById_ExistingEntity() throws Exception {
        // Arrange
        String id = "1";
        RobotDTO robot = new RobotDTO(id, "active", "order1", 10, "none");
        when(robotService.getRobotById(id)).thenReturn(robot);

        // Act & Assert
        mockMvc.perform(get("/robots/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.status").value("active"))
                .andExpect(jsonPath("$.currentOrderId").value("order1"));
    }

    @Test
    void testGetRobotById_NonExistingEntity() throws Exception {
        // Arrange
        String id = "NonExistingId";
        when(robotService.getRobotById(id)).thenThrow(new EntityNotFoundException("Robot with ID " + id + " not found"));

        // Act & Assert
        mockMvc.perform(get("/robots/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateRobot() throws Exception {
        // Arrange
        RobotDTO newRobot = new RobotDTO(null, "active", "order1", 0, "none");
        RobotDTO createdRobot = new RobotDTO("1", "active", "order1", 0, "none");
        when(robotService.createRobot(any(RobotDTO.class))).thenReturn(createdRobot);

        // Act & Assert
        mockMvc.perform(post("/robots")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"active\",\"currentOrderId\":\"order1\",\"completedOrders\":0,\"errors\":\"none\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.status").value("active"))
                .andExpect(jsonPath("$.currentOrderId").value("order1"));
    }

    @Test
    void testUpdateRobotStatus_ExistingEntity() throws Exception {
        // Arrange
        String id = "1";
        String status = "inactive";
        RobotDTO updatedRobot = new RobotDTO(id, status, "order1", 10, "none");
        when(robotService.updateRobotStatus(eq(id), eq(status))).thenReturn(updatedRobot);

        // Act & Assert
        mockMvc.perform(put("/robots/{id}/status", id)
                .param("status", status))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.status").value(status));
    }

    @Test
    void testUpdateRobotStatus_NonExistingEntity() throws Exception {
        // Arrange
        String id = "NonExistingId";
        String status = "active";
        when(robotService.updateRobotStatus(eq(id), eq(status)))
                .thenThrow(new EntityNotFoundException("Robot with ID " + id + " not found"));

        // Act & Assert
        mockMvc.perform(put("/robots/{id}/status", id)
                .param("status", status))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteRobot_ExistingEntity() throws Exception {
        // Arrange
        String id = "1";
        doNothing().when(robotService).deleteRobot(id);

        // Act & Assert
        mockMvc.perform(delete("/robots/{id}", id))
                .andExpect(status().isOk());

        verify(robotService, times(1)).deleteRobot(id);
    }

    @Test
    void testDeleteRobot_NonExistingEntity() throws Exception {
        // Arrange
        String id = "NonExistingId";
        doThrow(new EntityNotFoundException("Robot with ID " + id + " not found")).when(robotService).deleteRobot(id);

        // Act & Assert
        mockMvc.perform(delete("/robots/{id}", id))
                .andExpect(status().isNotFound());
    }
}
