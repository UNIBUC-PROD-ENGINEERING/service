package ro.unibuc.hello.service;

import ro.unibuc.hello.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userEntity -> new User(userEntity.getId(), userEntity.getName(), userEntity.getRole(), userEntity.getPassword()))
                .collect(Collectors.toList());
    }
    

    public User getUserById(String id) throws EntityNotFoundException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        return new User(user.getId(), user.getName(), user.getRole(), user.getPassword());

    }

    public User saveUser(User User) {
        UserEntity user = new UserEntity(User.getId(), User.getName(), User.getRole());
        userRepository.save(user);
        return new User(user.getId(), user.getName(), user.getRole(), user.getPassword());
    }

    public User updateUser(String id, User User) throws EntityNotFoundException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        user.setName(User.getName());
        user.setRole(User.getRole());
        userRepository.save(user);
        return new User(user.getId(), user.getName(), user.getRole(), user.getPassword());
    }

    public void deleteUser(String id) throws EntityNotFoundException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        userRepository.delete(user);
    }
}
