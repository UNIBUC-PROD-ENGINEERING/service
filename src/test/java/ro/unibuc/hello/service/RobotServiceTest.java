package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.data.RobotEntity;
import ro.unibuc.hello.data.RobotRepository;
import ro.unibuc.hello.dto.RobotDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.ValidationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class RobotServiceTest {

    @Mock
    private RobotRepository robotRepository;

    @InjectMocks
    private RobotService robotService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRobots() {
        List<RobotEntity> entities = Arrays.asList(
                new RobotEntity("idle", null, 5, null),
                new RobotEntity("in_progress", "order2", 10, null)
        );
        when(robotRepository.findAll()).thenReturn(entities);

        List<RobotDTO> robots = robotService.getAllRobots();

        assertEquals(2, robots.size());
        assertNull(robots.get(0).getCurrentOrderId());
        assertEquals("order2", robots.get(1).getCurrentOrderId());
    }

    @Test
    void testGetRobotById_ExistingEntity() throws EntityNotFoundException {
        String id = "1";
        RobotEntity entity = new RobotEntity("idle", null, 5, null);
        entity.setId(id);
        when(robotRepository.findById(id)).thenReturn(Optional.of(entity));

        RobotDTO robot = robotService.getRobotById(id);

        assertNotNull(robot);
        assertEquals(id, robot.getId());
        assertNull(robot.getCurrentOrderId());
    }

    @Test
    void testGetRobotById_NonExistingEntity() {
        String id = "NonExistingId";
        when(robotRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> robotService.getRobotById(id));
    }

    @Test
    void testCreateRobot_Successful() {
        RobotDTO robotDTO = new RobotDTO(null, "idle", null, 5, null);
        RobotEntity entity = new RobotEntity("idle", null, 5, null);
        when(robotRepository.save(any(RobotEntity.class))).thenReturn(entity);

        RobotDTO createdRobot = robotService.createRobot(robotDTO);

        assertNotNull(createdRobot);
        assertNull(createdRobot.getCurrentOrderId());
    }

    @Test
    void testCreateRobot_InvalidStatus() {
        RobotDTO robotDTO = new RobotDTO(null, "unknown_status", null, 5, null);

        assertThrows(ValidationException.class, () -> robotService.createRobot(robotDTO));
    }

    @Test
    void testCreateRobot_CompletedOrdersNegative() {
        RobotDTO robotDTO = new RobotDTO(null, "idle", null, -1, null);

        assertThrows(ValidationException.class, () -> robotService.createRobot(robotDTO));
    }

    @Test
    void testCreateRobot_IdleWithOrder() {
        RobotDTO robotDTO = new RobotDTO(null, "idle", "order123", 5, null);

        assertThrows(ValidationException.class, () -> robotService.createRobot(robotDTO));
    }

    @Test
    void testCreateRobot_ErrorWithoutMessage() {
        RobotDTO robotDTO = new RobotDTO(null, "error", null, 5, null);

        assertThrows(ValidationException.class, () -> robotService.createRobot(robotDTO));
    }

    @Test
    void testUpdateRobotStatus_Successful() throws EntityNotFoundException {
        String id = "1";
        RobotEntity entity = new RobotEntity("in_progress", "order1", 5, null);
        entity.setId(id);
        when(robotRepository.findById(id)).thenReturn(Optional.of(entity));
        when(robotRepository.save(any(RobotEntity.class))).thenReturn(entity);

        RobotDTO updatedRobot = robotService.updateRobotStatus(id, "completed");

        assertNotNull(updatedRobot);
        assertEquals("completed", updatedRobot.getStatus());
    }

    @Test
    void testUpdateRobotStatus_InvalidTransition() {
        String id = "1";
        RobotEntity entity = new RobotEntity("completed", null, 5, null);
        entity.setId(id);
        when(robotRepository.findById(id)).thenReturn(Optional.of(entity));

        assertThrows(ValidationException.class, () -> robotService.updateRobotStatus(id, "in_progress"));
    }

    @Test
    void testUpdateRobotStatus_ErrorWithoutMessage() {
        String id = "1";
        RobotEntity entity = new RobotEntity("in_progress", "order1", 5, null);
        entity.setId(id);
        when(robotRepository.findById(id)).thenReturn(Optional.of(entity));

        assertThrows(ValidationException.class, () -> robotService.updateRobotStatus(id, "error"));
    }

    @Test
    void testUpdateRobotStatus_NonExistingEntity() {
        String id = "NonExistingId";
        when(robotRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> robotService.updateRobotStatus(id, "idle"));
    }

    @Test
    void testDeleteRobot_ExistingEntity() throws EntityNotFoundException {
        String id = "1";
        RobotEntity entity = new RobotEntity("idle", null, 5, null);
        entity.setId(id);
        when(robotRepository.findById(id)).thenReturn(Optional.of(entity));

        robotService.deleteRobot(id);

        verify(robotRepository, times(1)).delete(entity);
    }

    @Test
    void testDeleteRobot_NonExistingEntity() {
        String id = "NonExistingId";
        when(robotRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> robotService.deleteRobot(id));
    }
}
