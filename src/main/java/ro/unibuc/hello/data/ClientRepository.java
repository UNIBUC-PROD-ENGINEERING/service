package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<ClientEntity, String> {

    public ClientEntity findByName(String name);
    public ClientEntity findByEmail(String email);
}
