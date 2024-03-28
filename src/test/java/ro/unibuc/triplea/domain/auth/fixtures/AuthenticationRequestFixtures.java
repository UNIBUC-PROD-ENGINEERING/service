package ro.unibuc.triplea.domain.auth.fixtures;

import ro.unibuc.triplea.application.auth.dto.request.AuthenticationRequest;

public class AuthenticationRequestFixtures {

    public static AuthenticationRequest authenticationRequest(String username) {
        return AuthenticationRequest.builder()
                .email(username)
                .password("password")
                .build();
    }
}
