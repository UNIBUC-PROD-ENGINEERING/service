package ro.unibuc.hello.controller;

import java.util.List;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.RestaurantDTO;
import ro.unibuc.hello.service.RestaurantService;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurant/getAll")
    @ResponseBody
    public List<RestaurantDTO> getRestaurants() {
        return restaurantService.getRestaurants();
    }

    @GetMapping("/restaurant/get")
    @ResponseBody
    public RestaurantDTO getRestaurant(@RequestParam(name="id") String id)
    {
        return restaurantService.getRestaurant(id);
    }
}
