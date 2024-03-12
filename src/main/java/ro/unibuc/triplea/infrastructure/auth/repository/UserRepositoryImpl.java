package ro.unibuc.triplea.infrastructure.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ro.unibuc.triplea.domain.auth.model.entity.meta.User;
import ro.unibuc.triplea.domain.auth.repository.UserRepository;

import java.util.Optional;

@Component
@Primary
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return springDataUserRepository.findByUsername(email);
    }

    @Override
    public User save(User user) {
        return springDataUserRepository.save(user);
    }
}
