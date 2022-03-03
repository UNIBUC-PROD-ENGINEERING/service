package ro.unibuc.tbd.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.tbd.exception.NotFoundException;
import ro.unibuc.tbd.model.Meal;
import ro.unibuc.tbd.repository.MealRepository;

@Service
public class MealService {

    private final MealRepository repository;

    @Autowired
    MealService(MealRepository MealRepository) {
        this.repository = MealRepository;
    }

    public Meal getMealById(String mealId) {
        Optional<Meal> meal = repository.findById(mealId);
        if (meal.isPresent()) {
            return meal.get();
        }

        throw new NotFoundException("Meal not found.");
    }

    public Meal getMealByName(String mealName) {
        Optional<Meal> meal = repository.findByName(mealName);
        if (meal.isPresent()) {
            return meal.get();
        }

        throw new NotFoundException("Meal not found.");
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
            throw new NotFoundException("Meal not found.");
        }

        Meal Meal = optionalMeal.get();
        Meal.setName(request.name);
        Meal.setIngredients(request.ingredients);
        Meal.setPortionSize(request.portionSize);
        Meal.setPrice(request.price);

        return repository.save(Meal);
    }

    public void deleteMealById(String mealId) {
        repository.deleteById(mealId);
    }
}
