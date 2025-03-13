package ro.unibuc.hello.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import ro.unibuc.hello.models.Task;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByPartyId(String partyId);  // Get all tasks for a party
    List<Task> findByAssignedUserId(String userId); // Get tasks assigned to a user
}
