package ro.tripleaunibuc.domain.auth.repository;

import ro.tripleaunibuc.domain.auth.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);
}
