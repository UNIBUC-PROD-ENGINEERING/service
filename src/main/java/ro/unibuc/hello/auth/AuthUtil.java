package ro.unibuc.hello.auth;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthUtil {

    public static String getAuthenticatedUserId(HttpServletRequest request) {
        return (String) request.getAttribute("authenticatedUserId");
    }
}

