package ro.unibuc.prodeng.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.unibuc.prodeng.utils.JwtUtil;
import ro.unibuc.prodeng.model.UserEntity;
import ro.unibuc.prodeng.repository.UserRepository;
import ro.unibuc.prodeng.request.RegisterRequest;
import ro.unibuc.prodeng.request.LoginRequest;
import ro.unibuc.prodeng.response.UserResponse;
import ro.unibuc.prodeng.response.LoginResponse;
import ro.unibuc.prodeng.exception.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserResponse registerUser(RegisterRequest req) {
        if (userRepository.findByEmail(req.email()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + req.email());
        }
        UserEntity user = new UserEntity();
        user.setName(req.name());
        user.setEmail(req.email());
        user.setPasswordHash(passwordEncoder.encode(req.password()));
        user.setGroup(req.group());
        user.setIsAdmin(req.isAdmin());
        UserEntity saved = userRepository.save(user);
        return toResponse(saved);
    }

    public LoginResponse login(LoginRequest req) {
        UserEntity user = userRepository.findByEmail(req.email())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), Boolean.TRUE.equals(user.getIsAdmin()));
        return new LoginResponse(token, user.getName(), user.getEmail(), user.getIsAdmin());
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public UserResponse getUserById(String id) throws EntityNotFoundException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        return toResponse(user);
    }

    public UserEntity getUserEntityById(String id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    public UserResponse changeName(String id, String newName) throws EntityNotFoundException {
        UserEntity existing = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        existing.setName(newName);
        UserEntity saved = userRepository.save(existing);
        return toResponse(saved);
    }

    public void deleteUser(String id) throws EntityNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    public UserResponse getUserByEmail(String email) throws EntityNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(email));
        return toResponse(user);
    }

    public UserEntity getUserEntityByEmail(String email) throws EntityNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(email));
    }

    private UserResponse toResponse(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getGroup(),
                user.getIsAdmin()
        );
    }
}
