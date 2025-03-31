package ro.unibuc.hello.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.data.Role;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.request.LoginDto;
import ro.unibuc.hello.dto.request.RegisterDto;
import ro.unibuc.hello.dto.response.AuthDto;
import ro.unibuc.hello.exception.EntityAlreadyExistsException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ModelMapper modelMapper;

    public UserEntity loadUser(String username) {
        return userRepository.findByUsername(username)
                                .orElseThrow(EntityNotFoundException::new);
    }

    public AuthDto register(RegisterDto registerDto){
        userRepository.findByUsername(registerDto.getUsername())
                .ifPresent(user -> { throw new EntityAlreadyExistsException(); });
        
        var user = modelMapper.map(registerDto, UserEntity.class);
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        String token = jwtService.generateJwt(user);
        userRepository.save(user);

        return AuthDto.builder()
                    .username(registerDto.getUsername())
                    .email(registerDto.getEmail())
                    .role(user.getRole())
                    .token(token)
                    .build();
    }

    private UserEntity checkLoginDetails(LoginDto loginDto){
        var user = loadUser(loginDto.getUsername());
        if(!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new EntityNotFoundException("user");
        }
        return user;
    }

    private void authenticate(LoginDto loginDto){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()
                    ));
        }catch(Exception e){
            throw new EntityNotFoundException("user");
        }
    }

    public AuthDto login(LoginDto loginDto){
        var user = checkLoginDetails(loginDto);
        authenticate(loginDto);
        String token = jwtService.generateJwt(user);
        return AuthDto.builder()
                .username(loginDto.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }

    public void logout(){
        SecurityContextHolder.clearContext();
    }
}
