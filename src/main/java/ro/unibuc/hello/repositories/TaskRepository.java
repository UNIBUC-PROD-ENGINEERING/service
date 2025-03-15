package ro.unibuc.hello.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import ro.unibuc.hello.data.TaskEntity;

import java.util.List;

public interface TaskRepository extends MongoRepository<TaskEntity, String> {
    List<TaskEntity> findByPartyId(String partyId);  // Get all tasks for a party
    List<TaskEntity> findByAssignedUserId(String userId); // Get tasks assigned to a user
}