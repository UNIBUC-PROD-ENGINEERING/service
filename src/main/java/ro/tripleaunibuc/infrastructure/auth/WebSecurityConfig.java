package ro.tripleaunibuc.infrastructure.auth;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import ro.tripleaunibuc.infrastructure.auth.JwtTokenAuthorizationOncePerRequestFilter;
import ro.tripleaunibuc.infrastructure.auth.JwtUnAuthorizedResponseAuthenticationEntryPoint;
import ro.tripleaunibuc.domain.auth.service.JpaUserDetailsService;

import static ro.tripleaunibuc.domain.auth.util.RestConstants.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUnAuthorizedResponseAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final JwtTokenAuthorizationOncePerRequestFilter jwtAuthenticationTokenFilter;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(jpaUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        httpSecurity.csrf(csrfConfigurer ->
                csrfConfigurer.ignoringRequestMatchers(mvcMatcherBuilder.pattern(SECURED_API_URL),
                        PathRequest.toH2Console()));

        httpSecurity.headers(headersConfigurer ->
                headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin).cacheControl());

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.OPTIONS, SECURED_API_URL)).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, AUTH_URL)).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, REGISTER_URL)).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(SECURED_API_URL)).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(PathRequest.toH2Console()).authenticated()
                        .anyRequest().authenticated()
        ).addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

//        httpSecurity
//                .csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtUnAuthorizedResponseAuthenticationEntryPoint)
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers(HttpMethod.OPTIONS, SECURED_API_URL).permitAll()
//                .requestMatchers(HttpMethod.POST, AUTH_URL).permitAll()
//                .requestMatchers(HttpMethod.POST, REGISTER_URL).permitAll()
//                .requestMatchers(SECURED_API_URL).hasAnyRole("ADMIN", "USER")
//                .requestMatchers("/api/**").permitAll()
//                .and()
//                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // for H2 Console
//        httpSecurity.headers().frameOptions().sameOrigin().cacheControl();

        return httpSecurity.build();
    }

}