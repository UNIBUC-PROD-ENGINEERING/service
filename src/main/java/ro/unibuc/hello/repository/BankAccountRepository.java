package ro.unibuc.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.entity.BankAccount;

@Repository
public interface BankAccountRepository extends MongoRepository<BankAccount, String> {
}
