package ro.unibuc.hello.dto.security;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(@NotBlank String username, @NotBlank String password) {
}
