package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.UserDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserDTO findUserById(long id) {
        Optional<UserEntity> userEntityOptional = Optional.ofNullable(userRepository.findById(id));
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            return new UserDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getEmail());
        } else {
            throw new EntityNotFoundException("Can't find user with id " + id);
        }
    }

    public boolean createUser(UserDTO userDTO) {
        try {
            userRepository.save(new UserEntity(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail()));
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean updateUser(UserDTO userDTO) {
        Optional<UserEntity> userEntityOptional = Optional.ofNullable(userRepository.findById(userDTO.getId()));
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setFirstName(userDTO.getFirstName());
            userEntity.setLastName(userDTO.getLastName());
            userEntity.setEmail(userDTO.getEmail());
            userRepository.save(userEntity);
            return true;
        } else {
            throw new EntityNotFoundException("Can't find user with id " + userDTO.getId());
        }
    }

    public boolean deleteUser(long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public List<UserDTO> findUsersByName(String name) {
        List<UserEntity> userEntities = userRepository.findByNameContaining(name);
        if (userEntities == null) {
            throw new EntityNotFoundException("Can't find users");
        }
        List<UserDTO> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            users.add(new UserDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getEmail()));
        }
        return users;
    }

    public boolean addUser(UserDTO userDTO) {
        try {
            userRepository.save(new UserEntity(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail()));
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public List<UserDTO> findAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        if (userEntities == null) {
            throw new EntityNotFoundException("Can't find users");
        }
        List<UserDTO> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            users.add(new UserDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getEmail()));
        }
        return users;
    }
}
