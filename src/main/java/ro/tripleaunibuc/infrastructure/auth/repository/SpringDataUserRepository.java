package ro.tripleaunibuc.infrastructure.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tripleaunibuc.domain.auth.model.User;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
