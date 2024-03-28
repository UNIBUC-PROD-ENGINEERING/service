package ro.unibuc.triplea.infrastructure.auth.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.unibuc.triplea.domain.auth.model.entity.meta.Token;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenRepositoryImplTest {

    @Mock
    private SpringDataTokenRepository springDataTokenRepository;

    @InjectMocks
    private TokenRepositoryImpl tokenRepositoryImpl;

    private final Token token = new Token();

    @Test
    void findByTokenShouldDelegateAndReturnResult() {
        when(springDataTokenRepository.findByToken("testToken")).thenReturn(Optional.of(token));

        Optional<Token> result = tokenRepositoryImpl.findByToken("testToken");

        assertThat(result).isPresent().containsSame(token);
        verify(springDataTokenRepository, times(1)).findByToken("testToken");
    }

    @Test
    void findAllValidTokenByUserShouldDelegateAndReturnResult() {
        List<Token> tokens = List.of(token);
        when(springDataTokenRepository.findAllValidTokenByUser(1)).thenReturn(tokens);

        List<Token> result = tokenRepositoryImpl.findAllValidTokenByUser(1);

        assertThat(result).isNotEmpty().contains(token);
        verify(springDataTokenRepository, times(1)).findAllValidTokenByUser(1);
    }

    @Test
    void saveShouldDelegateAndReturnResult() {
        when(springDataTokenRepository.save(any(Token.class))).thenReturn(token);

        Token result = tokenRepositoryImpl.save(token);

        assertThat(result).isSameAs(token);
        verify(springDataTokenRepository, times(1)).save(token);
    }

    @Test
    void saveAllShouldDelegateAndReturnResult() {
        List<Token> tokens = List.of(token);
        when(springDataTokenRepository.saveAll(any(Iterable.class))).thenReturn(tokens);

        List<Token> result = tokenRepositoryImpl.saveAll(tokens);

        assertThat(result).hasSize(1).containsExactly(token);
        verify(springDataTokenRepository, times(1)).saveAll(tokens);
    }
}
