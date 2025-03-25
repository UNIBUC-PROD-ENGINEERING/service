package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RentRepository extends MongoRepository<Rent, String> {
    List<Rent> findByUserId(String userId);
    List<Rent> findByGameId(String gameId);
    List<Rent> findByUserIdAndGameIdAndIsReturnedFalse(String userId, String gameId);
}