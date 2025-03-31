package ro.unibuc.hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@Configuration
public class ExceptionResolverConfig {
    @Bean
    @Primary
    public HandlerExceptionResolver customHandlerExceptionResolver() {
        return new DefaultHandlerExceptionResolver();
    }
}
