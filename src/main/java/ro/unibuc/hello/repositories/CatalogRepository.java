package ro.unibuc.hello.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.unibuc.hello.models.CatalogEntity;
import ro.unibuc.hello.models.StudentEntity;

public interface CatalogRepository extends MongoRepository<CatalogEntity, String> {

    CatalogEntity findByStudent(StudentEntity student);

}
