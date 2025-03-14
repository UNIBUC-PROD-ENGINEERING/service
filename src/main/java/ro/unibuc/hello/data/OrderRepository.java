package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, String> {

    List<OrderEntity> findByWorkerId(String robotId);
    List<OrderEntity> findByStatus(String status);
    List<OrderEntity> findByItemId(String itemId);
}
