package ro.bitbrawlers.parking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.bitbrawlers.parking.data.ParkingSpotEntity;
import ro.bitbrawlers.parking.data.ParkingSpotRepository;
import ro.bitbrawlers.parking.dto.CountDto;

import java.util.List;

@Component
public class ParkingSpotService {
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    public List<ParkingSpotEntity> findAll() {
        return parkingSpotRepository.findAll();
    }

    public ParkingSpotEntity findSpotById(Integer id) {
        return parkingSpotRepository.findSpotById(id);
    }
    public CountDto countSpots() {
        return new CountDto(parkingSpotRepository.countEmptySpots(), parkingSpotRepository.totalSpots());
    }


}
