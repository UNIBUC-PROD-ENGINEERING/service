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
                new RobotEntity("idle", "order1", 5, "none"),
                new RobotEntity("active", "order2", 10, "error")
        );
        when(robotRepository.findAll()).thenReturn(entities);

        List<RobotDTO> robots = robotService.getAllRobots();

        assertEquals(2, robots.size());
        assertEquals("order1", robots.get(0).getCurrentOrderId());
        assertEquals("order2", robots.get(1).getCurrentOrderId());
    }

    @Test
    void testGetRobotById_ExistingEntity() throws EntityNotFoundException {
        String id = "1";
        RobotEntity entity = new RobotEntity("idle", "order1", 5, "none");
        entity.setId(id);
        when(robotRepository.findById(id)).thenReturn(Optional.of(entity));

        RobotDTO robot = robotService.getRobotById(id);

        assertNotNull(robot);
        assertEquals(id, robot.getId());
        assertEquals("order1", robot.getCurrentOrderId());
    }

    @Test
    void testGetRobotById_NonExistingEntity() {
        String id = "NonExistingId";
        when(robotRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> robotService.getRobotById(id));
    }

    @Test
    void testCreateRobot() {
        RobotDTO robotDTO = new RobotDTO(null, "idle", "order1", 5, "none");
        RobotEntity entity = new RobotEntity("idle", "order1", 5, "none");
        when(robotRepository.save(any(RobotEntity.class))).thenReturn(entity);

        RobotDTO createdRobot = robotService.createRobot(robotDTO);

        assertNotNull(createdRobot);
        assertEquals("order1", createdRobot.getCurrentOrderId());
    }

    @Test
    void testUpdateRobotStatus_ExistingEntity() throws EntityNotFoundException {
        String id = "1";
        String status = "active";
        RobotEntity entity = new RobotEntity("idle", "order1", 5, "none");
        entity.setId(id);
        when(robotRepository.findById(id)).thenReturn(Optional.of(entity));
        when(robotRepository.save(any(RobotEntity.class))).thenReturn(entity);

        RobotDTO updatedRobot = robotService.updateRobotStatus(id, status);

        assertNotNull(updatedRobot);
        assertEquals(status, updatedRobot.getStatus());
    }

    @Test
    void testUpdateRobotStatus_NonExistingEntity() {
        String id = "NonExistingId";
        String status = "active";
        when(robotRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> robotService.updateRobotStatus(id, status));
    }

    @Test
    void testDeleteRobot_ExistingEntity() throws EntityNotFoundException {
        String id = "1";
        RobotEntity entity = new RobotEntity("idle", "order1", 5, "none");
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
