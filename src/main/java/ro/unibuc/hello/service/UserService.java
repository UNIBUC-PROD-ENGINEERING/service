package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.Produs;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepo;
import ro.unibuc.hello.dto.ProdusDTO;
import ro.unibuc.hello.dto.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserService {
    @Autowired
    UserRepo repo ;

    public UserDTO getUser(String id) {
          return entityToDTO(repo.findById(id).get());
    }

    public void createUser(UserDTO user) {
        repo.save( toEntity(user));
    }

    public List<UserDTO> getAll() {
        return repo.findAll().stream().map(this::entityToDTO).collect(Collectors.toList());

    }

    public UserEntity toEntity(UserDTO userDTO) {
        return new UserEntity(userDTO.getId(), userDTO.getEmail(), userDTO.getParola());
    }

    public boolean updateuser(UserDTO userDTO) {
        UserDTO found = entityToDTO(repo.findById(userDTO.getId()).orElse(null));
        if(found != null) {
            found.setEmail(userDTO.getEmail());
            found.setParola(userDTO.getParola());
            repo.save(toEntity(found));
            return true;
        }
        return false;
    }

    public boolean deleteuser(String id) {
        UserDTO found =  entityToDTO(repo.findById(id).orElse(null));
        if(found != null) {
            repo.delete(toEntity(found));
            return true;
        }
        return false;
    }
   public UserDTO entityToDTO(UserEntity user){
        return new UserDTO(user.getId(),user.getEmail(), user.getParola());
   }
}
