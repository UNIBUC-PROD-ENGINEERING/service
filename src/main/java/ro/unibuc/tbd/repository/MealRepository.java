package ro.unibuc.tbd.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.tbd.model.Meal;

@Repository
public interface MealRepository extends MongoRepository<Meal, String> {

    Optional<Meal> findByName(String name);
}
