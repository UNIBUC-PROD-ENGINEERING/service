package ro.unibuc.hello.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "mysupersecretkeyforjwt256bitssecurityyyyyyyy"; // Trebuie să aibă 32+ bytes

    private final SecretKey secret = Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // Generează cheia corect

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expiră în 10 ore
                .signWith(secret, SignatureAlgorithm.HS256) // Semnează corect cu `SecretKey`
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()  // ✅ Schimbat din `parserBuilder()` în `parser()`
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser() // ✅ Folosește `parser()`
                    .verifyWith(secret)
                    .build()
                    .parseSignedClaims(token);
            return !claims.getPayload().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parser() // Schimbat din `parserBuilder()` în `parser()`
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }
}
