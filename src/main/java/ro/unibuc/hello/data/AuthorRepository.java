package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends MongoRepository<AuthorEntity, String> {
    AuthorEntity findByName(String name);
    AuthorEntity findByNameAndBirthDate(String name, LocalDate birthDate);
    List<AuthorEntity> findAll();
}