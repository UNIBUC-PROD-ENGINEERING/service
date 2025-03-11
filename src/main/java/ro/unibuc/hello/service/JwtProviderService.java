package ro.unibuc.hello.service;

import org.springframework.security.core.Authentication;

public interface JwtProviderService {
    String generateToken(Authentication authentication);

    String getUsernameFromToken(String token);

    boolean validateToken(String token);
}
