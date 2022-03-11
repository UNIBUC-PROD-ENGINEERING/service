package ro.unibuc.tbd.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.tbd.model.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    public List<Order> findByClientId(String clientId);

}
