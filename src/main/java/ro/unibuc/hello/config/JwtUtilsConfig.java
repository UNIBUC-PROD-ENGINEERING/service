package ro.unibuc.hello.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="jwt-utils")
public class JwtUtilsConfig {

    @Value("${jwt-utils.extract-value}")
    private int extractValue;
    public Integer extractValue() {
        return extractValue;
    }
}