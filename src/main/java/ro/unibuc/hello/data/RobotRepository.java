package ro.unibuc.hello.data;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RobotRepository extends MongoRepository<RobotEntity, String> {

    Optional<RobotEntity> findById(String id);

    List<RobotEntity> findByStatus(String status);

    List<RobotEntity> findByCompletedOrders(Integer orders);
}
