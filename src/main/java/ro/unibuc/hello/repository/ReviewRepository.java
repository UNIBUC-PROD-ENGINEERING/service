package ro.unibuc.hello.repository;

import ro.unibuc.hello.data.ReviewEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Sort;
import java.util.List;

public interface ReviewRepository extends MongoRepository<ReviewEntity, String> {

    // Metoda care returnează review-urile sortate după rating (descrescător sau crescător)
    List<ReviewEntity> findAll(Sort sort);

    // Returnează review-urile cu rating mai mare de 3
    List<ReviewEntity> findByRatingGreaterThan(int rating, Sort sort);

    // Returnează review-urile cu rating mai mic sau egal cu 3
    List<ReviewEntity> findByRatingLessThanEqual(int rating, Sort sort);

}
