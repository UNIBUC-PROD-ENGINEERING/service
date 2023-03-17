package ro.unibuc.hello.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.UserDto;
import ro.unibuc.hello.entity.ProjectEntity;
import ro.unibuc.hello.entity.UserEntity;
import ro.unibuc.hello.util.PasswordUtil;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService() { }


    public UserEntity saveUser(UserDto userDTO) {

        UserEntity userEntity = new UserEntity();

        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());

        String securePassword = PasswordUtil.generateSecurePassword(userDTO.getPassword(), userDTO.getEmail());
        userEntity.setPassword(securePassword);

        userEntity.setEmail(userDTO.getEmail());

        return userRepository.save(userEntity);
    }

    public UserEntity getUser(String userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        return user.get();
    }

    public UserEntity getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

}
