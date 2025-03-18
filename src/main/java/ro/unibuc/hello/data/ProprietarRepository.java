package ro.unibuc.hello.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ro.unibuc.hello.data.ProprietarEntity;

@Repository
public interface ProprietarRepository extends MongoRepository<ProprietarEntity, String> {
    Optional<ProprietarEntity> findById(String id);
}
