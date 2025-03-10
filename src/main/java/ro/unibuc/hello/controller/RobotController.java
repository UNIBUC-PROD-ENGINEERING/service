package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.RobotDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.RobotService;

import java.util.List;

@RestController
@RequestMapping("/robots")
public class RobotController {

    @Autowired
    private RobotService robotService;

    @GetMapping
    public List<RobotDTO> getAllRobots() {
        return robotService.getAllRobots();
    }

    @GetMapping("/{id}")
    public RobotDTO getRobotById(@PathVariable String id) throws EntityNotFoundException {
        return robotService.getRobotById(id);
    }

    @PostMapping
    public RobotDTO createRobot(@RequestBody RobotDTO robotDTO) {
        return robotService.createRobot(robotDTO);
    }

    @PutMapping("/{id}/status")
    public RobotDTO updateRobotStatus(@PathVariable String id, @RequestParam String status) throws EntityNotFoundException {
        return robotService.updateRobotStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteRobot(@PathVariable String id) throws EntityNotFoundException {
        robotService.deleteRobot(id);
    }
}
