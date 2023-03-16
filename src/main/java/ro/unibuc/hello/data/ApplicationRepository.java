package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.entity.ApplicationEntity;
import ro.unibuc.hello.entity.UserEntity;

import java.util.List;

@Repository
public interface ApplicationRepository extends MongoRepository<ApplicationEntity, String> {

    public List<ApplicationEntity> findByProjectId(String id);

}