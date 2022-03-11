package ro.unibuc.tbd.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.tbd.model.Meal;
import ro.unibuc.tbd.repository.MealRepository;

@Service
public class MealService {

    private final MealRepository repository;

    @Autowired
    MealService(MealRepository mealRepository) {
        this.repository = mealRepository;
    }

    public Meal getMealById(String mealId) {
        Optional<Meal> meal = repository.findById(mealId);
        if (meal.isPresent()) {
            return meal.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal not found.");
    }

    public Meal getMealByName(String mealName) {
        Optional<Meal> meal = repository.findByName(mealName);
        if (meal.isPresent()) {
            return meal.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal not found.");
    }

    public List<Meal> getAllMeals() {
        return repository.findAll();
    }

    public Meal createMeal(Meal meal) {
        return repository.save(meal);
    }

    public Meal updateMeal(String mealId, Meal request) {
        Optional<Meal> optionalMeal = repository.findById(mealId);
        if (optionalMeal.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal not found.");
        }

        Meal meal = optionalMeal.get();
        meal.updateMeal(request);

        return repository.save(meal);
    }

    public Meal deleteMealById(String mealId) {
        Optional<Meal> optionalMeal = repository.findById(mealId);
        if (optionalMeal.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal not found.");
        }

        Meal meal = optionalMeal.get();

        repository.deleteById(mealId);
        return meal;
    }
}
