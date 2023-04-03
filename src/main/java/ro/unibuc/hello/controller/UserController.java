package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.UserDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Component
class UserService {

    @Autowired
    UserRepository userRepository;

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
