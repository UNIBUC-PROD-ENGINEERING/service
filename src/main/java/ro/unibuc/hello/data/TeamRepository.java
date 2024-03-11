package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<TeamEntity,String> {
    TeamEntity findByName(String name);
}
