package ro.unibuc.hello.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.unibuc.hello.models.StudentEntity;

public interface StudentRepository extends MongoRepository<StudentEntity, String> {
    StudentEntity findByFirstNameAndLastName(String firstName, String lastName);
}
