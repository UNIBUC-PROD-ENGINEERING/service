package ro.unibuc.hello.controller;

import java.util.List;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.ClientDTO;
import ro.unibuc.hello.dto.RestaurantDTO;
import ro.unibuc.hello.service.RestaurantService;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/get-all")
    @ResponseBody
    public List<RestaurantDTO> getRestaurants() {
        return restaurantService.getRestaurants();
    }

    @GetMapping("/get")
    @ResponseBody
    public RestaurantDTO getRestaurant(@RequestParam(name = "id") String id) {
        return restaurantService.getRestaurant(id);
    }

    @PostMapping("/insert")
    @ResponseBody
    public RestaurantDTO insertRestaurant(@RequestParam(name = "name") String name,
                                          @RequestParam(name = "address") String address,
                                          @RequestParam(name = "email") String email,
                                          @RequestParam(name = "ordersId") List<String> ordersID) {
        return restaurantService.insertRestaurant(name, address, email, ordersID);
    }

    @PutMapping("/update")
    @ResponseBody
    public RestaurantDTO updateClient(@RequestParam(name = "id") String id,
                                      @RequestParam(name = "name") String name,
                                      @RequestParam(name = "address") String address,
                                      @RequestParam(name = "email") String email,
                                      @RequestParam(name = "ordersId") List<String> ordersID) {
        return restaurantService.updateRestaurant(id, name, address, email, ordersID);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public String restaurantClient(@RequestParam(name = "id") String id) {
        return restaurantService.deleteRestaurant(id);
    }
}
