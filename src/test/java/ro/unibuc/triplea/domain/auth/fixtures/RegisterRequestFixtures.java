package ro.unibuc.triplea.domain.auth.fixtures;

import ro.unibuc.triplea.application.auth.dto.request.RegisterRequest;

public class RegisterRequestFixtures {

    public static RegisterRequest registerRequest() {
        return RegisterRequest.builder()
                .password("password")
                .build();
    }
}
