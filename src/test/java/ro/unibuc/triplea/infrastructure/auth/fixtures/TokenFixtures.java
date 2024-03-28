package ro.unibuc.triplea.infrastructure.auth.fixtures;

import ro.unibuc.triplea.domain.auth.model.entity.meta.Token;
import ro.unibuc.triplea.domain.auth.model.enums.TokenType;

public class TokenFixtures {

    public static Token revokedNotExpiredToken(String token) {
        return new Token(1, token, TokenType.BEARER, true, false, UserFixtures.user("username"));
    }

    public static Token validToken(String token) {
        return new Token(1, token, TokenType.BEARER, false, false, UserFixtures.user("username"));
    }

    public static Token validTokenUsername(String validToken, String username) {
        return new Token(1, validToken, TokenType.BEARER, false, false, UserFixtures.user(username));
    }
}
