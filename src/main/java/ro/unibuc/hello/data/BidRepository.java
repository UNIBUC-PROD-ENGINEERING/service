package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BidRepository extends MongoRepository<BidEntity, String> {
    // Existing methods...
    List<BidEntity> findByItemId(String itemId);
    List<BidEntity> findByItemIdOrderByAmountDesc(String itemId);
    List<BidEntity> findByBidderName(String bidderName);
    List<BidEntity> findByItemIdAndBidderName(String itemId, String bidderName);

    // Add these new methods for email-based queries
    List<BidEntity> findByEmail(String email);
    List<BidEntity> findByItemIdAndEmail(String itemId, String email);
    List<BidEntity> findByItemIdAndEmailOrderByAmountDesc(String itemId, String email);
}