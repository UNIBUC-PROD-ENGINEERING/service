package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.dto.Listing;
import ro.unibuc.hello.dto.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    @Query(" 'productId' : ?0 }")
    Product findProductById(String productId);
}
