package ro.unibuc.tbd.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.tbd.model.Restaurant;
import ro.unibuc.tbd.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{restaurantId}")
    public Restaurant getRestaurantById(@PathVariable String restaurantId) {
        return restaurantService.getRestaurantById(restaurantId);
    }

    @PostMapping
    public Restaurant registerRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantService.createRestaurant(restaurant);
    }

    @PatchMapping("/{restaurantId}")
    public Restaurant updateRestaurant(@PathVariable String restaurantId,
            @RequestBody Restaurant restaurant) {
        return restaurantService.updateRestaurant(restaurantId, restaurant);
    }

    @DeleteMapping("/{restaurantId}")
    public Restaurant deleteRestaurant(@PathVariable String restaurantId) {
        return restaurantService.deleteRestaurantById(restaurantId);
    }
}
