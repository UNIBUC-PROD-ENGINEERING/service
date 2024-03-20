package ro.unibuc.triplea.infrastructure.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.unibuc.triplea.domain.auth.model.entity.meta.User;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
