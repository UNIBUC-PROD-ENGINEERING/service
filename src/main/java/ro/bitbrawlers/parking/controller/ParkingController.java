package ro.bitbrawlers.parking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.bitbrawlers.parking.data.ParkingSpotEntity;
import ro.bitbrawlers.parking.dto.CountDto;
import ro.bitbrawlers.parking.service.ParkingSpotService;

import java.util.List;

@Controller
public class ParkingController {

    @Autowired
    private ParkingSpotService parkingSpotService;

    @GetMapping("/spots")
    @ResponseBody
    public List<ParkingSpotEntity> findAll() {
        return parkingSpotService.findAll();
    }

    @GetMapping("/spots/{id}")
    @ResponseBody
    public ParkingSpotEntity findSpotById(@PathVariable Integer id) {
        return parkingSpotService.findSpotById(id);
    }

    @GetMapping("/count")
    @ResponseBody
    public CountDto countEmptySpots() {
        return parkingSpotService.countSpots();
    }

}
