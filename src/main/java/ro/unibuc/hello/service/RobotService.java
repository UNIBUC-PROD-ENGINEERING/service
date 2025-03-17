package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.RobotEntity;
import ro.unibuc.hello.data.RobotRepository;
import ro.unibuc.hello.dto.RobotDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.ValidationException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RobotService {

    @Autowired
    private RobotRepository robotRepository;

    private static final Set<String> VALID_STATUSES = Set.of("idle", "in_progress", "completed", "error", "active", "inactive");

    public List<RobotDTO> getAllRobots() {
        List<RobotEntity> entities = robotRepository.findAll();
        return entities.stream()
                .map(entity -> new RobotDTO(entity.getId(), entity.getStatus(), entity.getCurrentOrderId(), entity.getCompletedOrders(), entity.getErrors()))
                .collect(Collectors.toList());
    }

    public RobotDTO getRobotById(String id) throws EntityNotFoundException {
        RobotEntity entity = robotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Robot with ID " + id + " not found"));
        return new RobotDTO(entity.getId(), entity.getStatus(), entity.getCurrentOrderId(), entity.getCompletedOrders(), entity.getErrors());
    }

    public RobotDTO createRobot(RobotDTO robotDTO) {
        validateRobot(robotDTO);

        RobotEntity robot = new RobotEntity(
                robotDTO.getStatus(),
                robotDTO.getCurrentOrderId(),
                robotDTO.getCompletedOrders(),
                robotDTO.getErrors()
        );
        robotRepository.save(robot);
        return new RobotDTO(robot.getId(), robot.getStatus(), robot.getCurrentOrderId(), robot.getCompletedOrders(), robot.getErrors());
    }

    public RobotDTO updateRobotStatus(String id, String newStatus) throws EntityNotFoundException {
        RobotEntity robot = robotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Robot with ID " + id + " not found"));

        validateStatusChange(robot.getStatus(), newStatus);

        robot.setStatus(newStatus);
        robot.setLastUpdatedAt(java.time.LocalDateTime.now());

        if ("error".equals(newStatus) && (robot.getErrors() == null || robot.getErrors().isEmpty())) {
            throw new ValidationException("Error field must be set when robot status is 'error'");
        }

        robotRepository.save(robot);
        return new RobotDTO(robot.getId(), robot.getStatus(), robot.getCurrentOrderId(), robot.getCompletedOrders(), robot.getErrors());
    }

    public void deleteRobot(String id) throws EntityNotFoundException {
        RobotEntity robot = robotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Robot with ID " + id + " not found"));
        robotRepository.delete(robot);
    }

    private void validateRobot(RobotDTO robotDTO) {
        if (!VALID_STATUSES.contains(robotDTO.getStatus())) {
            throw new ValidationException("Invalid status: " + robotDTO.getStatus());
        }

        if (robotDTO.getCompletedOrders() != null && robotDTO.getCompletedOrders() < 0) {
            throw new ValidationException("Completed orders cannot be negative");
        }

        if ("error".equals(robotDTO.getStatus()) && (robotDTO.getErrors() == null || robotDTO.getErrors().isEmpty())) {
            throw new ValidationException("Error field must be set when robot status is 'error'");
        }

        if ("idle".equals(robotDTO.getStatus()) && robotDTO.getCurrentOrderId() != null) {
            throw new ValidationException("Robot cannot have an order while in 'idle' status");
        }

        if ("error".equals(robotDTO.getStatus()) && robotDTO.getCurrentOrderId() != null) {
            throw new ValidationException("Robot cannot have an order while in 'error' status");
        }
    }
    private void validateStatusChange(String oldStatus, String newStatus) {
        if (!VALID_STATUSES.contains(newStatus)) {
            throw new ValidationException("Invalid status: " + newStatus);
        }
    
        if ("completed".equals(oldStatus) && "in_progress".equals(newStatus)) {
            throw new ValidationException("Cannot transition from 'completed' to 'in_progress'");
        }
    }
    
}
