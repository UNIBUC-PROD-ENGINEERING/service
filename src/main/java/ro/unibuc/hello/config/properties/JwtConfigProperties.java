package ro.unibuc.hello.config.properties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "security.jwt")
public record JwtConfigProperties(@NotEmpty String secret, @Positive Long expirationTime) {
}
