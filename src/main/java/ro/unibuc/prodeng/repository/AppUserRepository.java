package ro.unibuc.prodeng.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ro.unibuc.prodeng.model.AppUserEntity;

@Repository
public interface AppUserRepository extends MongoRepository<AppUserEntity, String> {
    Optional<AppUserEntity> findByEmail(String email);
}
