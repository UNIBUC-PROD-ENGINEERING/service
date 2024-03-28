package ro.unibuc.triplea.domain.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.unibuc.triplea.application.auth.dto.request.AuthenticationRequest;
import ro.unibuc.triplea.application.auth.dto.request.RegisterRequest;
import ro.unibuc.triplea.application.auth.dto.response.AuthenticationResponse;
import ro.unibuc.triplea.domain.auth.fixtures.AuthenticationRequestFixtures;
import ro.unibuc.triplea.domain.auth.fixtures.RegisterRequestFixtures;
import ro.unibuc.triplea.domain.auth.model.entity.meta.Token;
import ro.unibuc.triplea.domain.auth.model.entity.meta.User;
import ro.unibuc.triplea.domain.auth.repository.TokenRepository;
import ro.unibuc.triplea.domain.auth.repository.UserRepository;
import ro.unibuc.triplea.infrastructure.auth.JwtService;
import ro.unibuc.triplea.infrastructure.auth.fixtures.UserFixtures;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void registerShouldCreateNewUserAndGenerateTokens() {
        RegisterRequest request = RegisterRequestFixtures.registerRequest();

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        AuthenticationResponse response = authenticationService.register(request);

        assertThat(response.getAccessToken()).isEqualTo("accessToken");
        assertThat(response.getRefreshToken()).isEqualTo("refreshToken");
        verify(userRepository, times(1)).save(any(User.class));
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void authenticateShouldValidateUserAndGenerateTokens() {
        AuthenticationRequest request = AuthenticationRequestFixtures.authenticationRequest("user@example.com");
        User user = UserFixtures.user("user@example.com");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("newAccessToken");
        when(jwtService.generateRefreshToken(user)).thenReturn("newRefreshToken");

        AuthenticationResponse response = authenticationService.authenticate(request);

        assertThat(response.getAccessToken()).isEqualTo("newAccessToken");
        assertThat(response.getRefreshToken()).isEqualTo("newRefreshToken");
        verify(authenticationManager, times(1)).authenticate(any());
        verify(tokenRepository, atLeastOnce()).save(any(Token.class));
    }

    @Test
    void refreshTokenShouldGenerateNewAccessToken() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        MockHttpServletResponse response = new MockHttpServletResponse();
        User user = new User();
        user.setUsername("user@example.com");
        String refreshToken = "validRefreshToken";
        String newAccessToken = "newAccessToken";

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + refreshToken);

        when(userRepository.findByEmail(user.getUsername())).thenReturn(Optional.of(user));

        when(jwtService.extractUsername(refreshToken)).thenReturn(user.getUsername());
        when(jwtService.isTokenValid(refreshToken, user)).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn(newAccessToken);

        authenticationService.refreshToken(request, response);

        ObjectMapper objectMapper = new ObjectMapper();
        AuthenticationResponse authResponse = objectMapper.readValue(response.getContentAsString(), AuthenticationResponse.class);

        assertThat(authResponse.getAccessToken()).isEqualTo(newAccessToken);
        assertThat(authResponse.getRefreshToken()).isEqualTo(refreshToken);
        verify(jwtService, times(1)).generateToken(user);
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

}