package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<TaskEntity, String> {

    TaskEntity findByTitle(String title);
    List<TaskEntity> findByImportance(String importance);
    List<TaskEntity> findByDueDate(Date dueDate);
    List<TaskEntity> findAllByDueDate(Date dueDate);
}
