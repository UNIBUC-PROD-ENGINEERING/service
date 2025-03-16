package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.hello.dto.vehicle.VehicleDTO;
import ro.unibuc.hello.exceptions.vehicle.VehicleConflictException;
import ro.unibuc.hello.service.VehicleService;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAll() {
        List<VehicleDTO> vehicles = vehicleService.getAll();
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping
    public ResponseEntity<?> addVehicle(@RequestBody VehicleDTO vehicleDTO) {
        try {
            VehicleDTO vehicleResponse = vehicleService.addVehicle(vehicleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (VehicleConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}