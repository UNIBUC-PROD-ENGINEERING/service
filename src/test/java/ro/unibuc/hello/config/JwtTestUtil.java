package ro.unibuc.hello.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtTestUtil {

    private static final SecretKey secretKey = Keys.hmacShaKeyFor("mysupersecretkeyforjwt256bitssecurityyyyyyyy".getBytes());

    public static String generateTestToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 oră
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
