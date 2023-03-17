package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.unibuc.hello.entity.ProjectEntity;

public interface ProjectRepository extends MongoRepository<ProjectEntity, String> { }
