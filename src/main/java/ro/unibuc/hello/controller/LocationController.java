package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.data.LocationRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Location;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.LocationService;
@Controller
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/getLocationByAddress")
    @ResponseBody
    public Location getLocationByAddress(@RequestParam(name="address", required = true, defaultValue = "Bulevardul General Paul Teodorescu 4, București 061344") String address) throws  EntityNotFoundException{
        return locationService.getLocationByAddress(address);
    }

    @PostMapping
    @ResponseBody
    public boolean addLocation(@RequestParam(name="address", required = true) String address, @RequestParam(name="name", required = true) String name, @RequestParam(name="phoneNumber", required = true) String phoneNumber){
        return locationService.addLocation(new Location(address, name, phoneNumber));
    }

}
