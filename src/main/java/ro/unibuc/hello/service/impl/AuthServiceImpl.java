package ro.unibuc.hello.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.dto.security.AuthRequestDTO;
import ro.unibuc.hello.dto.security.JwtAuthResponseDTO;
import ro.unibuc.hello.entity.security.UserEntity;
import ro.unibuc.hello.exception.EntityAlreadyExistsException;
import ro.unibuc.hello.repository.security.UsersRepository;
import ro.unibuc.hello.service.AuthService;
import ro.unibuc.hello.service.JwtProviderService;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProviderService jwtProviderService;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtProviderService jwtProviderService, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtProviderService = jwtProviderService;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JwtAuthResponseDTO login(AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticate(authRequestDTO.username(), authRequestDTO.password());
        return new JwtAuthResponseDTO(jwtProviderService.generateToken(authentication));
    }

    @Override
    public JwtAuthResponseDTO register(AuthRequestDTO authRequestDTO) {
        if (usersRepository.existsByUsername(authRequestDTO.username())) {
            throw new EntityAlreadyExistsException("Username already exists");
        }

        UserEntity userEntity = UserEntity.builder()
                .username(authRequestDTO.username())
                .password(passwordEncoder.encode(authRequestDTO.password()))
                .roles(Set.of())
                .build();
        usersRepository.save(userEntity);

        Authentication authentication = authenticate(authRequestDTO.username(), authRequestDTO.password());
        return new JwtAuthResponseDTO(jwtProviderService.generateToken(authentication));
    }

    private Authentication authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
