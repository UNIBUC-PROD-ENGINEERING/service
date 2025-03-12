package ro.unibuc.hello.data.promotions;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PromotionRepository extends MongoRepository<PromotionEntity, String> {
    List<PromotionEntity> findByActiveTrue();
    List<PromotionEntity> findByActiveTrueAndStartDateBeforeAndEndDateAfter(
            LocalDateTime now, LocalDateTime now2);
}