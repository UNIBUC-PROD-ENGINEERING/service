package ro.unibuc.hello.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.AuctionRepository;
import ro.unibuc.hello.data.BidRepository;
import ro.unibuc.hello.data.ItemRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.AuctionWithItem;
import ro.unibuc.hello.dto.BidWithAuction;
import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.dto.UserPostRequest;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.InvalidDataException;

@Component
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    public List<User> getAllUsers() {
        List<UserEntity> entities = userRepository.findAll();
        return entities.stream()
            .map(User::new)
            .collect(Collectors.toList());
    }

    public User getUserById(String id)  {
        UserEntity userEntity = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
                            
        return new User(userEntity);
    }
    }

    public List<AuctionWithItem> getUserAuctions(String id) {
        UserEntity userEntity = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        return auctionRepository.findByAuctioneer(userEntity).stream()
            .map(AuctionWithItem::new)
            .collect(Collectors.toList());
    }

    public List<BidWithAuction> getUserBids(String id) {
        UserEntity userEntity = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        return bidRepository.findByBidder(userEntity).stream()
            .map(BidWithAuction::new)
            .collect(Collectors.toList());
    }

    public User saveUser(UserPostRequest user) {
        UserEntity newUser = new UserEntity(user.getName(), user.getPassword(), user.getUsername());

        try {
            userRepository.save(newUser);
        } catch (DuplicateKeyException ex) {
            throw new InvalidDataException("Username already exists");
        }

        return new User(newUser);
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
            .map(User::new)
            .collect(Collectors.toList());
    }

    public User updateUser(String id, UserPostRequest user) {
        UserEntity userEntity = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        userEntity.setName(user.getName());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());

        try {
            userRepository.save(userEntity);
        } catch (DuplicateKeyException ex) {
            throw new InvalidDataException("Username already exists");
        }

        return new User(userEntity);
    }

    public void deleteUser(String id) {
        UserEntity entity = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
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
