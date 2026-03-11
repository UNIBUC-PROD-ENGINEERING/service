package ro.unibuc.prodeng.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.prodeng.model.ComponentEntity;

@Repository
public interface ComponentRepository extends MongoRepository<ComponentEntity, String> {

}