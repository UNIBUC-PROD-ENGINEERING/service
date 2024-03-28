package ro.unibuc.triplea.infrastructure.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ro.unibuc.triplea.domain.auth.model.entity.meta.Token;
import ro.unibuc.triplea.domain.auth.repository.TokenRepository;
import ro.unibuc.triplea.infrastructure.auth.fixtures.TokenFixtures;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private LogoutService logoutService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void logoutShouldRevokeTokenAndClearSecurityContextWhenTokenIsValid() {
        final String tokenValue = "valid.token";
        Token storedToken = TokenFixtures.validToken(tokenValue);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + tokenValue);
        when(tokenRepository.findByToken(tokenValue)).thenReturn(java.util.Optional.of(storedToken));

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, times(1)).save(any(Token.class));
        assertThat(storedToken.isExpired()).isTrue();
        assertThat(storedToken.isRevoked()).isTrue();
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void logoutShouldDoNothingWhenAuthHeaderIsMissing() {
        when(request.getHeader("Authorization")).thenReturn(null);

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, never()).findByToken(anyString());
        verify(tokenRepository, never()).save(any(Token.class));
    }

    @Test
    void logoutShouldDoNothingWhenTokenIsNotBearer() {
        when(request.getHeader("Authorization")).thenReturn("Token nonBearerToken");

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, never()).findByToken(anyString());
        verify(tokenRepository, never()).save(any(Token.class));
    }
}

