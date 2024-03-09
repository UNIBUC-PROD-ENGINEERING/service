package ro.tripleaunibuc.infrastructure.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ro.tripleaunibuc.domain.auth.model.User;
import ro.tripleaunibuc.domain.auth.repository.UserRepository;

import java.util.Optional;

@Component
@Primary
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return springDataUserRepository.findByUsername(username);
    }
}
