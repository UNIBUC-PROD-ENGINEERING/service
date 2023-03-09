package ro.unibuc.hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean("RestTemplate")
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
