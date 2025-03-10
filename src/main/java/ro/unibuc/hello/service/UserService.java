package ro.unibuc.hello.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.data.User;
import ro.unibuc.hello.dto.request.RegisterDto;
import ro.unibuc.hello.dto.response.UserDto;
import ro.unibuc.hello.dto.response.UserListDto;
import ro.unibuc.hello.exception.EntityAlreadyExistsException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper;

    public UserDto createUser(RegisterDto registerDto){
        userRepository.findByUsername(registerDto.getUsername())
                .ifPresent(user -> { throw new EntityAlreadyExistsException(); });

        var student = modelMapper.map(registerDto, User.class);

        var savedEntity = userRepository.save(student);

        return modelMapper.map(savedEntity, UserDto.class);
    }

    public User loadUser(String username) {
        return userRepository.findByUsername(username)
                                .orElseThrow(()->new EntityNotFoundException(username));
    }
    public UserDto getUser(String username) {
        var user = loadUser(username);
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto updateUser(String username, RegisterDto registerDto){
        var user = userRepository.findByUsername(username)
                                .orElseThrow(EntityAlreadyExistsException::new); // Do not let user update username to existing one
        userRepository.save(modelMapper.map(registerDto, User.class));
        return modelMapper.map(registerDto,UserDto.class);
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
