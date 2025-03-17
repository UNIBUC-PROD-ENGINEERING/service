package ro.unibuc.hello.controller;

import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.LocationEntity;
import ro.unibuc.hello.service.LocationService;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<LocationEntity> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/filter/rating")
    public List<LocationEntity> getLocationsByRating(@RequestParam double minRating) {
        return locationService.getLocationsByRating(minRating);
    }

    @GetMapping("/filter/price")
    public List<LocationEntity> getLocationsByPrice(@RequestParam double maxPrice) {
        return locationService.getLocationsByPrice(maxPrice);
    }

    @GetMapping("/filter/discount")
    public List<LocationEntity> getLocationsByDiscountPoints(@RequestParam int maxPoints) {
        return locationService.getLocationsByDiscountPoints(maxPoints);
    }
}
