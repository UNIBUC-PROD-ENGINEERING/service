package ro.unibuc.hello.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.request.LoginDto;
import ro.unibuc.hello.dto.request.RegisterDto;
import ro.unibuc.hello.dto.response.UserDto;
import ro.unibuc.hello.dto.response.UserListDto;
import ro.unibuc.hello.exception.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserService implements userDetails{
    
    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(authority)
        );
    }

    public UserDto register(RegisterDto registerDto){
        userRepository.findByUsername(registerDto.getUsername())
                .ifPresent(user -> { throw new EntityAlreadyExistsException(); });

        var student = modelMapper.map(registerDto, UserEntity.class);

        var savedEntity = userRepository.save(student);

        return modelMapper.map(savedEntity, UserDto.class);
    }

    public UserEntity loadUser(String username) {
        return userRepository.findByUsername(username)
                                .orElseThrow(()->new EntityNotFoundException(username));
    }
    public UserDto getUser(String username) {
        var user = loadUser(username);
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto login(LoginDto LoginDto){

    }

    public UserDto updateUser(String username, RegisterDto registerDto){
        var user = userRepository.findByUsername(username)
                                .orElseThrow(EntityAlreadyExistsException::new); // Do not let user update username to existing one
        
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());

        // Save the updated user
        userRepository.save(user);

         // Save the updated user
        userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }


    public UserListDto getAll() {
        System.out.println("Getting all users...");
        return new UserListDto(userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList());
    }

    public void delete(String username){
        var user = loadUser(username);
        userRepository.delete(user);
    }
}
