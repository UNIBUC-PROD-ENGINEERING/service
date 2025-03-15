package ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import main.java.ro.unibuc.hello.data.ProductEntity;


@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {

    ProductEntity findByName(String name);
    List<ProductEntity> findByDescription(String description);

}
