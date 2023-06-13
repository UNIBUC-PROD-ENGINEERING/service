package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ro.unibuc.hello.dto.OrderDTO;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, String> {

}
