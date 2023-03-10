package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
public interface ProjectRepository extends MongoRepository<ProjectEntity, String> { }
