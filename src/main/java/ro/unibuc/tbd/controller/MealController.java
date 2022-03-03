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
import ro.unibuc.tbd.model.Meal;
import ro.unibuc.tbd.service.MealService;

@RestController
@RequestMapping("/api/meal")
public class MealController {

    private final MealService mealService;

    @Autowired
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    public List<Meal> getAllMeals() {
        return mealService.getAllMeals();
    }

    @GetMapping("/name/{mealName}")
    public Meal getMealByName(@PathVariable String mealName) {
        return mealService.getMealByName(mealName);
    }

    @GetMapping("/id/{mealId}")
    public Meal getMealById(@PathVariable String mealId) {
        return mealService.getMealById(mealId);
    }

    @PostMapping
    public Meal registerMeal(@RequestBody Meal meal) {
        return mealService.createMeal(meal);
    }

    @PatchMapping("/{mealId}")
    public Meal updateMeal(@PathVariable String mealId, @RequestBody Meal meal) {
        return mealService.updateMeal(mealId, meal);
    }

    @DeleteMapping("/{mealId}")
    public void deleteMeal(@PathVariable String mealId) {
        mealService.deleteMealById(mealId);
    }
}
