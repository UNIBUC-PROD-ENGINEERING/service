package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.user.User;
import ro.unibuc.hello.data.user.UserDTO;
import ro.unibuc.hello.data.user.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    

    public String saveUser(UserDTO userDTO)
    {
        User userToSave = new User(userDTO);
        userRepository.save(userToSave);
        return userToSave.getId();
    }

    public User getUserById(String id) throws Exception
    {
        return userRepository.findById(id).orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
    }

    public void deleteUserById(String id) throws Exception
    {
 
        
        // Apoi ștergem utilizatorul
        userRepository.deleteById(id);
    }
    
    // Metode noi pentru gestionarea cardurilor de fidelitate
    

}
