package ro.unibuc.hello.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.ItemRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.dto.ItemCreateRequest;
import ro.unibuc.hello.dto.ItemUpdateRequest;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Component
public class ItemsService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;


    public List<Item> getAllItems() {
        List<ItemEntity> entities = itemRepository.findAll();
        return entities.stream()
                .map(entity -> new Item(entity))
                .collect(Collectors.toList());
    }

    public Item getItemById(String id) {
        ItemEntity entity = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        return new Item(entity);
    }

    public Item saveItem(ItemCreateRequest item) {
        UserEntity user = userRepository.findById(item.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ItemEntity newItem = new ItemEntity(item.getName(), item.getDescription(), user);
        newItem = itemRepository.save(newItem);
        return new Item(newItem);
    }

    public List<Item> saveAll(List<Item> Items) {
        List<ItemEntity> entities = Items.stream()
                .map(Item -> {
                    ItemEntity entity = new ItemEntity();
                    entity.setName(Item.getName());
                    entity.setDescription(Item.getDescription());
                    return entity;
                })
                .collect(Collectors.toList());

        List<ItemEntity> savedEntities = itemRepository.saveAll(entities);

        return savedEntities.stream()
                .map(entity -> new Item(entity))
                .collect(Collectors.toList());
    }

    public Item updateItem(String id, ItemUpdateRequest item) {
        ItemEntity entity = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        
        entity.setDescription(item.getDescription());
        entity.setName(item.getName());

        itemRepository.save(entity);
        return new Item(entity);
    }

    public void deleteItem(String id) {
        ItemEntity entity = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        itemRepository.delete(entity);
    }

    public void deleteAllItems() {
        itemRepository.deleteAll();
    }
}
