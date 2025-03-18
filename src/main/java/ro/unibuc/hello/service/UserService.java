package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUseri(){
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(String id){
        return userRepository.findById(id);
    }

    public User createUser(User userDTO) {
        UserEntity userEntity = new UserEntity(
            userDTO.getId(), 
            userDTO.getNume(), 
            userDTO.getPrenume(), 
            userDTO.getEmail()
        );
        userEntity = userRepository.save(userEntity);
        return new User(
            userEntity.getId(),
            userEntity.getNume(),
            userEntity.getPrenume(),
            userEntity.getEmail()
        );
    }

    public Optional<UserEntity> updateUser(String id, UserEntity userDetails){
        return userRepository.findById(id).map(existingUser ->{
            existingUser.setNume(userDetails.getNume());
            existingUser.setPrenume(userDetails.getPrenume());
            existingUser.setEmail(userDetails.getEmail());

            return userRepository.save(existingUser);
        });
    }
}
