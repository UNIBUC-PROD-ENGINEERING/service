package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;



import ro.unibuc.hello.dto.UserRequestDTO;
import ro.unibuc.hello.dto.UserResponseDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.model.User;
import ro.unibuc.hello.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserResponseDTO createUser(UserRequestDTO userDto) throws Exception {
        if (userRepository.findByMail(userDto.getMail()).isPresent()) {
            throw new DuplicateKeyException("Email already exists: " + userDto.getMail());
        } else if (userRepository.findByPhoneNumber(userDto.getPhoneNumber()).isPresent()) {
            throw new DuplicateKeyException("Phone number " +
                                            userDto.getPhoneNumber() + 
                                            " already used");
        }

        User newUser = userDto.toEntity();

        String passwordHash = passwordEncoder.encode(userDto.getPassword());
        newUser.setPasswordHash(passwordHash);

        userRepository.save(newUser);

        return UserResponseDTO.toDTO(newUser);
    }


    public UserResponseDTO getUserById(String id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .map(UserResponseDTO::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public UserResponseDTO getUserByMail(String mail) throws EntityNotFoundException {
        return userRepository.findByMail(mail)
                .map(UserResponseDTO::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found with mail: " + mail));
    }

    public boolean existsByMail(String mail) {
        return userRepository.findByMail(mail)
                .isPresent();
    }
}
