package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ro.unibuc.hello.data.Role;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.request.LoginDto;
import ro.unibuc.hello.dto.request.RegisterDto;
import ro.unibuc.hello.dto.response.AuthDto;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.EntityAlreadyExistsException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private AuthService authService;

    private UserEntity user;
    private RegisterDto registerDto;
    private LoginDto loginDto;

    @BeforeEach
    void beforeEach(){
        SecurityContextHolder.clearContext();
        user = UserEntity.builder()
                .username("user")
                .password("password")
                .role("USER")
                .build();
        registerDto = RegisterDto.builder()
                .username("user")
                .email("email")
                .password("password")
                .build();
        loginDto = new LoginDto("user","password");
    }

    @Test
    void userShouldAlreadyExistAtRegister(){
        when(userRepository.findByUsername(registerDto.getUsername())).thenReturn(Optional.ofNullable(user));

        assertThrows(EntityAlreadyExistsException.class,()->authService.register(registerDto));

    }

    @Test
    void shouldRegisterSuccessfully(){
        when(userRepository.findByUsername(registerDto.getUsername())).thenReturn(Optional.empty());
        when(modelMapper.map(registerDto,UserEntity.class)).thenReturn(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encoded");
        when(userRepository.save(user)).thenReturn(user);
        when(jwtService.generateJwt(user)).thenReturn("token");
        AuthDto authDto = authService.register(registerDto);
        assertNotNull(authDto);
        assertEquals("user",authDto.getUsername());
        assertEquals(Role.USER,authDto.getRole());
        assertEquals("token",authDto.getToken());

    }

    @Test
    void userShouldNotExistAtLogin(){
        when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,()->authService.login(loginDto));

    }

    @Test
    void shouldHaveWrongPassword(){
        loginDto.setPassword("wrongPassword");
        when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.ofNullable(user));
        when(passwordEncoder.matches(loginDto.getPassword(),user.getPassword())).thenReturn(false);
        assertThrows(EntityNotFoundException.class,()->authService.login(loginDto));
    }

    @Test
    void shouldFailToAuthenticate(){
        when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.ofNullable(user));
        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(true);

        when(authenticationManager.authenticate(any())).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class,()->authService.login(loginDto));
    }

    @Test
    void shouldLoginUser(){
        when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.ofNullable(user));
        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtService.generateJwt(user)).thenReturn("token");

        AuthDto response = authService.login(loginDto);

        assertNotNull(response);
        assertEquals("user", response.getUsername());
        assertEquals(Role.USER, response.getRole());
        assertEquals("token", response.getToken());
    }

    @Test
    void shouldLogoutUser(){
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        authService.logout();
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }


}