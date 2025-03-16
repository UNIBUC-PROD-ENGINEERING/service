package ro.unibuc.hello.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ro.unibuc.hello.dto.vehicle.VehicleDTO;
import ro.unibuc.hello.exceptions.vehicle.VehicleConflictException;
import ro.unibuc.hello.model.Vehicle;
import ro.unibuc.hello.repository.UserRepository;
import ro.unibuc.hello.repository.VehicleRepository;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public VehicleService(VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    public List<VehicleDTO> getAll() {
        return vehicleRepository.findAll()
                                .stream()
                                .map(VehicleDTO::toDTO)
                                .collect(Collectors.toList());
        
    }
    
    public VehicleDTO addVehicle(VehicleDTO vehicleDTO) {
        if (vehicleRepository.existsByLicensePlate(vehicleDTO.getLicensePlate())) {
            throw new VehicleConflictException("License plate already used.");
        }

        if (userRepository.findById(vehicleDTO.getUserId()).isEmpty()) {
            throw new VehicleConflictException("Owner not stored in the system.");
        }

        vehicleRepository.save(vehicleDTO.toEntity());

        return vehicleDTO;
    }
}
