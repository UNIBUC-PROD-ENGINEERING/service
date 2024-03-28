package ro.unibuc.triplea.infrastructure.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ro.unibuc.triplea.domain.auth.model.entity.meta.Token;
import ro.unibuc.triplea.domain.auth.model.enums.TokenType;
import ro.unibuc.triplea.domain.auth.repository.TokenRepository;
import ro.unibuc.triplea.infrastructure.auth.fixtures.TokenFixtures;
import ro.unibuc.triplea.infrastructure.auth.fixtures.UserFixtures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private FilterChain filterChain;

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldProceedChainForExcludedPaths() throws ServletException, IOException {
        request.setServletPath("/api/v1/auth/login");
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void shouldProceedChainWhenAuthHeaderIsMissing() throws ServletException, IOException {
        request.setServletPath("/api/v1/auth/login");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void shouldAuthenticateUserWithValidToken() throws ServletException, IOException {
        String validToken = "valid.token";
        request.addHeader("Authorization", "Bearer " + validToken);
        request.setServletPath("/some/protected/resource");

        when(jwtService.extractUsername(validToken)).thenReturn("user@example.com");
        when(jwtService.isTokenValid(eq(validToken), any(UserDetails.class))).thenReturn(true);
        when(tokenRepository.findByToken(validToken)).thenReturn(Optional.of(TokenFixtures.validTokenUsername(validToken, "user@example.com")));

        UserDetails userDetails = new User("user@example.com", "password", new ArrayList<>());
        when(userDetailsService.loadUserByUsername("user@example.com")).thenReturn(userDetails);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat("user@example.com").isEqualTo(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
