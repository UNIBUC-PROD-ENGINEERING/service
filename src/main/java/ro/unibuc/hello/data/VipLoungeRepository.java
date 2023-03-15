package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VipLoungeRepository extends MongoRepository<VipLoungeEntity, String> {
    VipLoungeEntity findByEntryPrice(String entryPrice);
}
