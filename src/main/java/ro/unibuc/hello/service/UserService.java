package ro.unibuc.hello.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.ItemRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.dto.UserPost;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private MongoTemplate mongoTemplate;  // Inject MongoTemplate

    public List<User> getAllUsers() {
        List<UserEntity> entities = userRepository.findAllWithItems();
        return entities.stream()
                .map(entity -> new User(entity.getId(), entity.getName(),  entity.getUsername(), entity.getItems()))
                .collect(Collectors.toList());
    }

    public User getUserById(String id) throws EntityNotFoundException {
        Optional<UserEntity> optionalEntity = userRepository.findByIdWithItems(id);
        UserEntity entity = optionalEntity.orElseThrow(() -> new EntityNotFoundException(id));
        // System.out.println(optionalEntity);
        return new User(entity.getId(), entity.getName(),  entity.getUsername(), entity.getItems());
   }

    public User saveUser(UserPost user) {
        UserEntity newUser = new UserEntity(user.getName(), user.getPassword(), user.getUsername());
        userRepository.save(newUser);

        user.getOwnedItems().forEach(item -> {
            ItemEntity newItem = new ItemEntity(item.getName(), item.getDescription());
            newItem.setOwner(newUser);
            itemRepository.save(newItem);
        });

        return new User(newUser.getId(), newUser.getName(),  newUser.getUsername(), newUser.getItems());
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
                .map(entity -> new User( entity.getName(),  entity.getUsername()))
                .collect(Collectors.toList());
    }

    //face update la itemuri
    public User updateUser(String id, UserPost user) throws EntityNotFoundException {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        
        entity.setName(user.getName());
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        List<ItemEntity> newItems = entity.updateItems(user.getOwnedItems());
        
        itemRepository.saveAll(newItems);
        userRepository.save(entity);
        return new User(entity.getId(), entity.getName(),  entity.getUsername(), entity.getItems());
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
