package main.java.ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import main.java.ro.unibuc.hello.data.ProprietarEntity;

@Repository
public interface ProprietarRepository extends MongoRepository<ProprietarEntity, String> {
    
}
