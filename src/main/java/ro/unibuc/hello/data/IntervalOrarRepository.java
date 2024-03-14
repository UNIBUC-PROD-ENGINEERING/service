package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IntervalOrarRepository extends MongoRepository<IntervalOrarEntity, String> {
    
}
