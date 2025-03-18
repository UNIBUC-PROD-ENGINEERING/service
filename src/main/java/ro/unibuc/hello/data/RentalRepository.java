package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RentalRepository extends MongoRepository<Rental, String> {
    Optional<Rental> findByUserIdAndGameIdAndReturnDateIsNull(String userId, String gameId);
}
