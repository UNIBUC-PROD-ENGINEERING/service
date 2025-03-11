package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    private User convertToDTO(UserEntity userEntity) {
        User userDTO = new User();
        userDTO.setId(userEntity.getId());
        userDTO.setPhoneNumber(userEntity.getPhoneNumber());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setFullName(userEntity.getFullName());
        userDTO.setAge(userEntity.getAge());
        return userDTO;
    }

    public User createUser(UserEntity user) {
        UserEntity savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll()
                             .stream()
                             .map(this::convertToDTO)
                             .collect(Collectors.toList());
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id)
                             .map(this::convertToDTO);
    }

    public Optional<User> updateUser(String id, UserEntity updatedUserDTO) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    updatedUserDTO.setId(id);
                    UserEntity savedUser = userRepository.save(updatedUserDTO);
                    return convertToDTO(savedUser);
                });
    }

    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                             .map(this::convertToDTO);
    }

    public Optional<User> getUserByFullName(String fullName) {
        return userRepository.findByFullName(fullName)
                             .map(this::convertToDTO);
    }
    
}
