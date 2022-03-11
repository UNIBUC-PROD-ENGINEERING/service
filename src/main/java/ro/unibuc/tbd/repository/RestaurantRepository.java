package ro.unibuc.tbd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.unibuc.tbd.model.Restaurant;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

}
