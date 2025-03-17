package ro.unibuc.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.entity.Group;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {
}
