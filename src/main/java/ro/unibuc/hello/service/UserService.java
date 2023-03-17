package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.CarEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.CarsDTO;
import ro.unibuc.hello.dto.RegisterUserDTO;
import ro.unibuc.hello.dto.UserDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Component

public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getUsers(){
        ArrayList<UserEntity> users = new ArrayList<>();

        //userRepository.findAll().forEach(userEntity -> usersDTOs.add(new UserDTO(userEntity)));

        return  userRepository.findAll();

    }

    public UserDTO getUserById(String id){
        UserEntity user = userRepository.findById(id).orElse(null);

        if (user == null)
            throw new EntityNotFoundException(id);
        else
            return new UserDTO(user.firstName, user.lastName);

    }

    public RegisterUserDTO registerUser(String firstName, String lastName,String userName, String password){
        UserEntity user = new UserEntity( firstName, lastName, userName, password);

        return new RegisterUserDTO(userRepository.save(user) );
    }

    public void changeUsername(String id, String newUserName)
    {
        UserEntity user = userRepository.findById(id).orElse(null);

        if (user == null)
            throw new EntityNotFoundException(id);

        user.setUserName(newUserName);
        userRepository.save(user);
    }

    public void deleteUserById(String id){
        userRepository.deleteById(id);
    }
}
