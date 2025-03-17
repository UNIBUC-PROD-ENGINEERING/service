package ro.unibuc.hello.controller;

import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.FoodEntity;
import ro.unibuc.hello.service.FoodService;
import java.util.List;

@RestController
@RequestMapping("/foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public List<FoodEntity> getAllFoods() {
        return foodService.getAllFoods();
    }

    @GetMapping("/filter/rating")
    public List<FoodEntity> getFoodsByRating(@RequestParam double minRating) {
        return foodService.getFoodsByRating(minRating);
    }

    @GetMapping("/filter/price")
    public List<FoodEntity> getFoodsByPrice(@RequestParam double maxPrice) {
        return foodService.getFoodsByPrice(maxPrice);
    }

    @GetMapping("/filter/discount")
    public List<FoodEntity> getFoodsByDiscountPoints(@RequestParam int maxPoints) {
        return foodService.getFoodsByDiscountPoints(maxPoints);
    }
}
