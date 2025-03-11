package ro.unibuc.hello.service;

import ro.unibuc.hello.dto.security.AuthRequestDTO;
import ro.unibuc.hello.dto.security.JwtAuthResponseDTO;

public interface AuthService {
    JwtAuthResponseDTO login(AuthRequestDTO authRequestDTO);

    JwtAuthResponseDTO register(AuthRequestDTO authRequestDTO);
}
