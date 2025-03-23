package ro.unibuc.hello.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.ItemRepository;
import ro.unibuc.hello.data.SessionEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.dto.ItemPost;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Component
public class ItemsService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionService sessionService;


    public List<Item> getAllItems() {
        List<ItemEntity> entities = itemRepository.findAll();
        return entities.stream()
                .map(entity -> new Item(entity.getName(), entity.getDescription(), entity.getOwner().getUsername()))
                .collect(Collectors.toList());
    }

    public Item getItemById(String id) {
        // Optional<ItemEntity> optionalEntity = itemRepository.findById(id);
        // System.out.println(optionalEntity);
        // ItemEntity entity = optionalEntity.orElseThrow(() -> new EntityNotFoundException(id));
        ItemEntity entity = itemRepository.findById(id)
                          .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        System.out.println(entity);
        return new Item(entity.getName(), entity.getDescription(), entity.getOwner().getUsername());
    }

    public List<Item> getItemsOwnedBy(UserEntity owner) {
        List<ItemEntity> items = itemRepository.findByOwner(owner);
        return items.stream()
            .map(entity -> new Item(entity.getName(), entity.getDescription(), entity.getOwner().getUsername()))
            .collect(Collectors.toList());
    }

    public Item saveItem(ItemPost item) {
        SessionEntity session = sessionService.getValidSession(item.getSessionId());
        UserEntity user = userRepository.findById(session.getUser().getId()).orElseThrow();
        ItemEntity newItem = new ItemEntity(item.getName(), item.getDescription(), user);
        itemRepository.save(newItem);
        return new Item(newItem.getName(), newItem.getDescription(), newItem.getOwner().getUsername());
    }

    public List<Item> saveAll(List<ItemPost> items) {
        List<ItemEntity> entities = items.stream()
                .map(item -> {
                    SessionEntity session = sessionService.getValidSession(item.getSessionId());
                    UserEntity user = userRepository.findById(session.getUser().getId()).orElseThrow();
                    ItemEntity entity = new ItemEntity();
                    entity.setName(item.getName());
                    entity.setDescription(item.getDescription());
                    entity.setOwner(user);
                    return entity;
                })
                .collect(Collectors.toList());

        List<ItemEntity> savedEntities = itemRepository.saveAll(entities);

        return savedEntities.stream()
                .map(entity -> new Item( entity.getName(), entity.getDescription(), entity.getOwner().getUsername()))
                .collect(Collectors.toList());
    }

    public Item updateItem(String id, Item item) throws EntityNotFoundException {
        ItemEntity entity = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        
        entity.setDescription(item.getDescription());
        entity.setName(item.getName());
        entity.setOwner(userRepository.findByUsername(item.getOwner()));
        
        itemRepository.save(entity);
        return new Item(entity.getName(), entity.getDescription(), entity.getOwner().getUsername());
    }

    public void deleteItem(String id) throws EntityNotFoundException {
        ItemEntity entity = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        itemRepository.delete(entity);
    }

    public void deleteAllItems() {
        itemRepository.deleteAll();
    }
}
