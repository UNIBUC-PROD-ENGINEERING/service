package ro.unibuc.triplea.domain.auth.repository;


import ro.unibuc.triplea.domain.auth.model.entity.meta.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    User save(User user);
}
