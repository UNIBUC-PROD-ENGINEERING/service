package ro.unibuc.triplea.infrastructure.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ro.unibuc.triplea.domain.auth.model.entity.meta.Token;
import ro.unibuc.triplea.domain.auth.repository.TokenRepository;

import java.util.List;
import java.util.Optional;

@Component
@Primary
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final SpringDataTokenRepository springDataTokenRepository;

    @Override
    public List<Token> findAllValidTokenByUser(Integer id) {
        return springDataTokenRepository.findAllValidTokenByUser(id);
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return springDataTokenRepository.findByToken(token);
    }

    @Override
    public List<Token> saveAll(Iterable<Token> entities) {
        return springDataTokenRepository.saveAll(entities);
    }

    @Override
    public Token save(Token entity) {
        return springDataTokenRepository.save(entity);
    }
}
