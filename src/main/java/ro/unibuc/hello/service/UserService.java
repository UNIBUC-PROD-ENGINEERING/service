import com.example.socialmedia.exception.EntityNotFoundException;
import com.example.socialmedia.model.User;
import com.example.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final AtomicLong counter = new AtomicLong();

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDto getUserById(String id) throws EntityNotFoundException {
        Optional<UserDto> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new EntityNotFoundException(id));
    }

    public UserDto saveUser(UserDto user) {
        return userRepository.save(user);
    }

    // --- SAVE ALL (creare în masă)
    public List<User> saveAllUsers(List<User> users) {
        return userRepository.saveAll(users);
    }

    // --- UPDATE
    public User updateUser(String id, User userData) throws EntityNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        // Actualizezi câmpurile dorite (exemplu)
        user.setUsername(userData.getUsername());
        user.setEmail(userData.getEmail());
        user.setFullName(userData.getFullName());
        // ... alte câmpuri

        userRepository.save(user);
        return user;
    }

    // --- DELETE
    public void deleteUser(String id) throws EntityNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        userRepository.delete(user);
    }

    // --- DELETE ALL
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
