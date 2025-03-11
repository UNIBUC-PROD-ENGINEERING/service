package ro.unibuc.hello.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.config.properties.JwtConfigProperties;
import ro.unibuc.hello.service.JwtProviderService;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtProviderServiceImpl implements JwtProviderService {
    private final JwtConfigProperties jwtConfigProperties;

    public JwtProviderServiceImpl(JwtConfigProperties jwtConfigProperties) {
        this.jwtConfigProperties = jwtConfigProperties;
    }

    @Override
    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .subject(authentication.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtConfigProperties.expirationTime()))
                .signWith(getPrivateKey())
                .compact();
    }

    @Override
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getPrivateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) getPrivateKey())
                    .build()
                    .parse(token);
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }

    private Key getPrivateKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfigProperties.secret()));
    }
}
