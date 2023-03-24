package ro.unibuc.hello.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.models.StudentEntity;

@Repository
public interface StudentRepository extends MongoRepository<StudentEntity, String> {
    StudentEntity findByFirstNameAndLastName(String firstName, String lastName);
}
