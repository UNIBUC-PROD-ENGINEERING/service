package ro.unibuc.prodeng.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.unibuc.prodeng.exception.EntityNotFoundException;
import ro.unibuc.prodeng.model.AppUserEntity;
import ro.unibuc.prodeng.repository.AppUserRepository;
import ro.unibuc.prodeng.request.ForgotPasswordRequest;
import ro.unibuc.prodeng.request.LoginRequest;
import ro.unibuc.prodeng.request.RegisterRequest;
import ro.unibuc.prodeng.response.LoginResponse;
import ro.unibuc.prodeng.utils.JwtUtil;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse register(RegisterRequest req) {
        if (appUserRepository.findByEmail(req.email()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + req.email());
        }
        AppUserEntity user = new AppUserEntity();
        user.setName(req.name());
        user.setEmail(req.email());
        user.setPasswordHash(passwordEncoder.encode(req.password()));
        user.setGroup(req.group());
        user.setIsAdmin(req.isAdmin());
        AppUserEntity saved = appUserRepository.save(user);
        String token = jwtUtil.generateToken(saved.getId(), saved.getEmail(), Boolean.TRUE.equals(saved.getIsAdmin()));
        return new LoginResponse(token, saved.getName(), saved.getEmail(), saved.getIsAdmin());
    }

    public LoginResponse login(LoginRequest req) {
        AppUserEntity user = appUserRepository.findByEmail(req.email())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), Boolean.TRUE.equals(user.getIsAdmin()));
        return new LoginResponse(token, user.getName(), user.getEmail(), user.getIsAdmin());
    }

    public void forgotPassword(ForgotPasswordRequest req) {
        AppUserEntity user = appUserRepository.findByEmail(req.email())
            .orElseThrow(() -> new RuntimeException("No account found for that email"));
        user.setPasswordHash(passwordEncoder.encode(req.newPassword()));
        appUserRepository.save(user);
    }

    public void deleteUser(String id) throws EntityNotFoundException {
        if (id == null || !appUserRepository.existsById(id)) {
            throw new EntityNotFoundException(id);
        }
        appUserRepository.deleteById(id);
    }
}
