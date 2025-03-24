package ro.unibuc.hello.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ro.unibuc.hello.data.User;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.LoginRequest;
import ro.unibuc.hello.dto.LoginResponse;
import ro.unibuc.hello.dto.RegisterRequest;
import ro.unibuc.hello.security.JwtUtil;

@Controller
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Authentication failed");
        }

        String token = jwtUtil.generateToken(loginRequest.getUsername());

        return new LoginResponse(token);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/register")
    public LoginResponse register(@RequestBody @Valid RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User(registerRequest.getUsername(), registerRequest.getPassword());
        userRepository.save(user);

        UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.unauthenticated(registerRequest.getUsername(), registerRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Authentication failed");
        }

        String token = jwtUtil.generateToken(registerRequest.getUsername());

        return new LoginResponse(token);
    }
}
