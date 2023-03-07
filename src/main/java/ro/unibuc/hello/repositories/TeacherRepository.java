package ro.unibuc.hello.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.unibuc.hello.models.TeacherEntity;

public interface TeacherRepository extends MongoRepository<TeacherEntity, String> {

    TeacherEntity findByFirstNameAndLastName(String firstName, String lastName);

}
