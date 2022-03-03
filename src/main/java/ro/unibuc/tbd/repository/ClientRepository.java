package ro.unibuc.tbd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.tbd.model.Client;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {

    public Client findByName(String name);

    public Client findByEmail(String email);
}
