package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<TaskEntity, String> {

    public TaskEntity findByTitle(String title);
    public List<TaskEntity> findByImportance(String importance);
}
