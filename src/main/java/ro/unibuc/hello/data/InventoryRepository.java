package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends MongoRepository<InventoryEntity, String> {

    List<InventoryEntity> findByName(String name);
    List<InventoryEntity> findByStockLessThan(Integer stock);
    List<InventoryEntity> findByThresholdLessThan(Integer threshold);
    List<InventoryEntity> findByItemId(String itemId);
}
