package main.java.ro.unibuc.hello.repository;

import main.java.ro.unibuc.hello.entity.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
    Optional<Client> findByName(String name);
}
