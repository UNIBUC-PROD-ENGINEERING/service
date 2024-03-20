package ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends MongoRepository<LoanEntity, String> {

    List <LoanEntity> findByClient (ClientEntity client);
}