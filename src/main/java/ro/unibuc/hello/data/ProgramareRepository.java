package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProgramareRepository extends MongoRepository<ProgramareEntity, String> {
    
}
