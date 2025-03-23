package ro.unibuc.hello.auth;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import ro.unibuc.hello.exception.UnauthorizedException;

@Component
public class AuthUtil {

    public static String getAuthenticatedUserId(HttpServletRequest request) {
        String userId = (String) request.getAttribute("authenticatedUserId");
        if (userId == null) {
            throw new UnauthorizedException("Unauthorized");
        }

        return userId;
    }
}

