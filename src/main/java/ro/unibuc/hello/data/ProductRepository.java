package ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {

    ProductEntity findByName(String name);
    List<ProductEntity> findByDescription(String description);

}
