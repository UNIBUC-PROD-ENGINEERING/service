package ro.unibuc.hello.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.ItemRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.dto.UserDetails;
import ro.unibuc.hello.dto.UserPostRequest;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<User> getAllUsers() {
        List<UserEntity> entities = userRepository.findAll();
        return entities.stream()
                .map(entity -> new User(entity))
                .collect(Collectors.toList());
    }

    public UserDetails getUserById(String id)  {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
                            
        List<Item> items = getUserItems(userEntity);
        return new UserDetails(userEntity, items);
   }

    public UserDetails saveUser(UserPostRequest user) {
        UserEntity newUser = new UserEntity(user.getName(), user.getPassword(), user.getUsername());
        userRepository.save(newUser);

        List<Item> items = getUserItems(newUser);
        return new UserDetails(newUser, items);
    }

    public List<User> saveAll(List<UserPostRequest> users) {
        List<UserEntity> entities = users.stream()
                .map(user -> {
                    UserEntity entity = new UserEntity();
                    entity.setName(user.getName());
                    entity.setUsername(user.getUsername());
                    entity.setPassword(user.getPassword());
                    return entity;
                })
                .collect(Collectors.toList());

        List<UserEntity> savedEntities = userRepository.saveAll(entities);

        return savedEntities.stream()
                .map(entity -> new User(entity))
                .collect(Collectors.toList());
    }

    public UserDetails updateUser(String id, UserPostRequest user) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        
        userEntity.setName(user.getName());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userRepository.save(userEntity);

        List<Item> items = getUserItems(userEntity);
        return new UserDetails(userEntity, items);
    }

    public void deleteUser(String id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        userRepository.delete(entity);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    private List<Item> getUserItems(UserEntity owner) {
        return itemRepository.findByOwner(owner).stream()
                .map(itemEntity -> new Item(itemEntity))
                .collect(Collectors.toList());
    }
}
