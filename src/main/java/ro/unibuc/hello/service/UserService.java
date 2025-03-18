package ro.unibuc.hello.service;

import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.UserDTO;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    private final AtomicLong counter = new AtomicLong();

    public UserDTO getUserById(String id) throws EntityNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        UserEntity user = optionalUser.orElseThrow(() -> new EntityNotFoundException(id));
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    }

    public UserDTO getUserByUsername(String username) throws EntityNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException(username);
        }
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    }

    public List<UserDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
    }

    public UserDTO saveUser(UserDTO userDTO) {
        UserEntity user = new UserEntity(
                Long.toString(counter.incrementAndGet()),
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPassword()
        );
        userRepository.save(user);
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public boolean authenticateUser(String username, String password) {
        UserEntity user = userRepository.findByUsername(username);
        return user != null && BCrypt.checkpw(password, user.getPassword());
    }
}
