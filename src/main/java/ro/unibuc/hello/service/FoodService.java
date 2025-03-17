package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.FoodEntity;
import ro.unibuc.hello.repositories.FoodRepository;
import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<FoodEntity> getAllFoods() {
        return foodRepository.findAll();
    }

    public List<FoodEntity> getFoodsByRating(double minRating) {
        return foodRepository.findByRatingGreaterThanEqual(minRating);
    }

    public List<FoodEntity> getFoodsByPrice(double maxPrice) {
        return foodRepository.findByPriceLessThanEqual(maxPrice);
    }

    public List<FoodEntity> getFoodsByDiscountPoints(int maxPoints) {
        return foodRepository.findByDiscountPointsRequiredLessThanEqual(maxPoints);
    }
}
