package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Creare utilizator
    public User createUser(User user) {
        // Verifica dacă exista deja un utilizator cu acelasi username
        if (!userRepository.findByUsernameContaining(user.getUsername()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }
    
        // Creeaza si salvează noul utilizator
        UserEntity userEntity = new UserEntity(user.getUsername(), user.getPassword());
        userEntity.setTier(user.getTier());
        userEntity.setExpirationDate(user.getExpirationDate());
        
        UserEntity savedEntity = userRepository.save(userEntity);
        return convertToDto(savedEntity);
    }
    
    public List<User> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    

    // Cautare utilizator dupa ID
    public User getUserById(String id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(this::convertToDto)
                         .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    // Cautare utilizatori dupa username
    public List<User> getUsersByUsername(String username) {
        List<UserEntity> users = userRepository.findByUsernameContaining(username);
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Stergere utilizator dupa ID
    public boolean deleteUserById(String id) {
        if (!userRepository.existsById(id)) {
            return false; 
        }
        userRepository.deleteById(id);
        return true;
    }
    

    // Conversie intre Entity și DTO
    private User convertToDto(UserEntity entity) {
        return new User(entity.getId(), entity.getUsername(), entity.getPassword(), entity.getTier(), entity.getExpirationDate());
    }
}


