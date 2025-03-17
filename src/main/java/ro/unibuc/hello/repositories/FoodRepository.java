package ro.unibuc.hello.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import ro.unibuc.hello.data.FoodEntity;

import java.util.List;

public interface FoodRepository extends MongoRepository<FoodEntity, String> {
    List<FoodEntity> findByName(String name);
    List<FoodEntity> findByRatingGreaterThanEqual(double minRating);
    List<FoodEntity> findByPriceLessThanEqual(double maxPrice);
    List<FoodEntity> findByDiscountPointsRequiredLessThanEqual(int maxPoints);
}
