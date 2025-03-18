package ro.unibuc.hello.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
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