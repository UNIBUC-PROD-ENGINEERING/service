package ro.unibuc.hello.dto.security;

import jakarta.validation.constraints.NotBlank;

public record JwtAuthResponseDTO(@NotBlank String accessToken) {
    private static final String TOKEN_TYPE = "Bearer";

    public String tokenType() {
        return TOKEN_TYPE;
    }
}
