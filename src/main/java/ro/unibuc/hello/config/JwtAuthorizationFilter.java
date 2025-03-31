package ro.unibuc.hello.config;

import ro.unibuc.hello.service.JwtService;
import ro.unibuc.hello.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Lazy
    private final UserService userService;

    @Qualifier("handlerExceptionResolver") // Explicitly specify which bean to use so testing doesn't break
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final JwtUtilsConfig jwtUtilsConfig;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            if (!request.getServletPath().contains("auth") && !request.getServletPath().contains("generation")) {
                response.setStatus(401);
            } else {
                filterChain.doFilter(request, response);
            }
            return;
        }

        try {
            String jwt = authHeader.substring(jwtUtilsConfig.extractValue());
            String username = jwtService.extractUsername(jwt);
            if (username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
                UserDetails userDetails = this.userService.loadUserByUsername(username);
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                System.out.println(authToken);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
            response.setStatus(401);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
