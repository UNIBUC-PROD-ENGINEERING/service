package ro.unibuc.triplea.domain.auth.repository;

import ro.unibuc.triplea.domain.auth.model.entity.meta.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository {

    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);

    List<Token> saveAll(Iterable<Token> entities);

    Token save(Token entity);
}
