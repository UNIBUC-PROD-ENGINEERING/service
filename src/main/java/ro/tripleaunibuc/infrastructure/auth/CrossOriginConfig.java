package ro.tripleaunibuc.infrastructure.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static org.springframework.web.cors.CorsConfiguration.ALL;

@Configuration
public class CrossOriginConfig implements WebMvcConfigurer {

    private static final List<String> PERMIT_METHODS = List.of(
            HttpMethod.OPTIONS.name(),
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.DELETE.name());

    private static final List<String> LOCALHOST = List.of("http://localhost:4200");

    private static final long MAX_AGE = 60 * 60 * 24L; // 24 hours

    @Bean
    public FilterRegistrationBean<CorsFilter> getCorsFilterRegistrationBean() {
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(LOCALHOST);
        config.setAllowedMethods(PERMIT_METHODS);
        config.setAllowedHeaders(List.of(ALL));
        config.setMaxAge(MAX_AGE);

        configurationSource.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(configurationSource));
        bean.setOrder(0);

        return bean;
    }
}