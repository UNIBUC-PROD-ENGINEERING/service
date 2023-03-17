package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {
    CustomerEntity findByName(String name);
}
