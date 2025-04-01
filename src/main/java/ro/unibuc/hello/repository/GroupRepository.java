package ro.unibuc.hello.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.entity.Group;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {
    List<Group> findAll();

}
