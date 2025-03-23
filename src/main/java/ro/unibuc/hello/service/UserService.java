package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.SessionEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.dto.UserDetails;
import ro.unibuc.hello.dto.UserPost;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.UsernameNotUniqueException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private SessionService sessionService;


    public List<User> getAllUsers() {
        List<UserEntity> entities = userRepository.findAll();
        return entities.stream()
            .map(entity -> new User(entity.getId(), entity.getName(),  entity.getUsername()))
            .collect(Collectors.toList());
    }

    public UserDetails getUserById(String id)  {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        List<Item> ownedItems = itemsService.getItemsOwnedBy(userEntity);
        return new UserDetails(userEntity.getId(), userEntity.getName(),  userEntity.getUsername(), ownedItems);
   }

    public UserDetails saveUser(UserPost user) {

        //check if username is unique
        if( userRepository.findByUsername( user.getUsername() ) == null){
            throw new UsernameNotUniqueException();
        }

        SessionEntity session = sessionService.getValidSession(user.getSessionId());
        UserEntity newUser = new UserEntity(user.getName(), user.getPassword(), user.getUsername());
        newUser = userRepository.save(newUser);

        List<Item> ownedItems = itemsService.getItemsOwnedBy(newUser);
        return new UserDetails(newUser.getId(), newUser.getName(),  newUser.getUsername(), ownedItems);
    }

    public List<User> saveAll(List<UserPost> users) {
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
            .map(entity -> new User(entity.getName(),  entity.getUsername()))
            .collect(Collectors.toList());
    }

    //face update la itemuri
    public UserDetails updateUser(String id, UserPost user) throws EntityNotFoundException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        
        userEntity.setName(user.getName());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        // List<ItemEntity> newItems = entity.updateItems(user.getOwnedItems());
        
        // itemRepository.saveAll(newItems);
        List<Item> ownedItems = itemsService.getItemsOwnedBy(userEntity);
        userRepository.save(userEntity);

        return new UserDetails(userEntity.getId(), userEntity.getName(),  userEntity.getUsername(), ownedItems);
    }

    public void deleteUser(String id) throws EntityNotFoundException {
        UserEntity entity = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        userRepository.delete(entity);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
