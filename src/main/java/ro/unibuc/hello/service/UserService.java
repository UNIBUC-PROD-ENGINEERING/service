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

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Creare utilizator
    public User createUser(User user) {
        UserEntity userEntity = new UserEntity(user.getUsername(), user.getPassword());
        userEntity.setTier(user.getTier());
        userEntity.setExpirationDate(user.getExpirationDate());
        UserEntity savedEntity = userRepository.save(userEntity);
        return convertToDto(savedEntity);
    }

    // Căutare utilizator după ID
    public User getUserById(String id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(this::convertToDto)
                         .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    // Căutare utilizatori după username
    public List<User> getUsersByUsername(String username) {
        List<UserEntity> users = userRepository.findByUsernameContaining(username);
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Ștergere utilizator după ID
    public void deleteUserById(String id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    // Conversie între Entity și DTO
    private User convertToDto(UserEntity entity) {
        return new User(entity.getId(), entity.getUsername(), entity.getPassword(), entity.getTier(), entity.getExpirationDate());
    }
}


