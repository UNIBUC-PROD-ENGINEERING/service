package ro.unibuc.triplea.infrastructure.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ro.unibuc.triplea.domain.auth.model.entity.meta.User;
import ro.unibuc.triplea.infrastructure.auth.fixtures.UserFixtures;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.core.userdetails.User.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtService, "secretKey", "mySecretKeyWhichIsVerySecretIndeedSoPleaseChangeMe");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 5 * 60 * 1000);
        ReflectionTestUtils.setField(jwtService, "refreshExpiration", 30 * 60 * 1000);
    }

    @Test
    void generateToken_ShouldGenerateValidToken() {
        User user = UserFixtures.userWithRoleUser("username");

        String token = jwtService.generateToken(user);
        assertThat(token).isNotNull().isNotEmpty();

        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(jwtService.getSignInKey()).build().parseClaimsJws(token);
        assertThat(claims.getBody().getSubject()).isEqualTo("username");
    }

    @Test
    void generateRefreshToken_ShouldGenerateValidRefreshToken() {
        User user = UserFixtures.userWithRoleUser("username");

        String refreshToken = jwtService.generateRefreshToken(user);
        assertThat(refreshToken).isNotNull().isNotEmpty();

        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(jwtService.getSignInKey()).build().parseClaimsJws(refreshToken);
        assertThat(claims.getBody().getSubject()).isEqualTo("username");
    }

    @Test
    void isTokenValid_ShouldReturnTrueForValidToken() {
        User user = UserFixtures.userWithRoleUser("username");

        String token = jwtService.generateToken(user);
        assertThat(token).isNotNull().isNotEmpty();

        UserDetails userDetails = withUsername("username")
                .password("password")
                .authorities(new ArrayList<>())
                .build();

        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertThat(isValid).isTrue();
    }

    @Test
    void isTokenExpired_ShouldDetectExpiredTokensAndThrowException() {
        User user = UserFixtures.userWithRoleUser("username");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", -1000L);

        String token = jwtService.generateToken(user);
        assertThat(token).isNotNull().isNotEmpty();

        assertThrows(ExpiredJwtException.class, () -> jwtService.isTokenExpired(token));

        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 5 * 60 * 1000);
    }
}
