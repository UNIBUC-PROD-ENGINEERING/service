package ro.unibuc.hello.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface UserListRepository extends MongoRepository<UserListEntity, String> {

    Optional<UserListEntity> findByUsernameAndToDoListAndIsOwner(String username, String toDoList, boolean isOwner);
    Optional<UserListEntity> findByUsernameAndToDoList(String username, String toDoList);

}
