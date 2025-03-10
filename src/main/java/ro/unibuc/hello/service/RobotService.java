package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.RobotEntity;
import ro.unibuc.hello.data.RobotRepository;
import ro.unibuc.hello.dto.RobotDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RobotService {

    @Autowired
    private RobotRepository robotRepository;

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
        RobotEntity robot = new RobotEntity(
                robotDTO.getStatus(),
                robotDTO.getCurrentOrderId(),
                robotDTO.getCompletedOrders(),
                robotDTO.getErrors()
        );
        robotRepository.save(robot);
        return new RobotDTO(robot.getId(), robot.getStatus(), robot.getCurrentOrderId(), robot.getCompletedOrders(), robot.getErrors());
    }

    public RobotDTO updateRobotStatus(String id, String status) throws EntityNotFoundException {
        RobotEntity robot = robotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Robot with ID " + id + " not found"));
        robot.setStatus(status);
        robot.setLastUpdatedAt(java.time.LocalDateTime.now()); 
        robotRepository.save(robot);
        return new RobotDTO(robot.getId(), robot.getStatus(), robot.getCurrentOrderId(), robot.getCompletedOrders(), robot.getErrors());
    }

    public void deleteRobot(String id) throws EntityNotFoundException {
        RobotEntity robot = robotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Robot with ID " + id + " not found"));
        robotRepository.delete(robot);
    }
}
