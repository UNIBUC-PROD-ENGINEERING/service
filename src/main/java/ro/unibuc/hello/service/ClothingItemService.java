package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.ClothingItemEntity;
import ro.unibuc.hello.data.ClothingItemRepository;
import ro.unibuc.hello.dto.ClothingItem;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ClothingItemService {
    @Autowired
    private ClothingItemRepository clothingItemRepository;


    public ClothingItem getClothingItemById(String id) throws EntityNotFoundException {
        Optional<ClothingItemEntity> entity = clothingItemRepository.findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException(id);
        }
        ClothingItemEntity entityValue = entity.get();
        return new ClothingItem(entityValue.id, entityValue.title, entityValue.description, entityValue.material, entityValue.size, entityValue.price);
    }

    public List<ClothingItem> getClothingItems() throws EntityNotFoundException {
        List<ClothingItemEntity> entities = clothingItemRepository.findAll();
        if (entities.toArray().length == 0) {
            throw new EntityNotFoundException();
        }
        List<ClothingItem> clothingItems = entities.stream().map(entity -> new ClothingItem(entity.id, entity.title, entity.description, entity.material, entity.size, entity.price)).collect(Collectors.toList());
        return clothingItems;
    }

    public String deleteItem(String id) {
        clothingItemRepository.deleteById(id);
        return "Item deleted successfully";
    }

    public String insert(ClothingItem clothingItem){
        final ClothingItemEntity clothingItemEntity = new ClothingItemEntity(clothingItem.id, clothingItem.title,
                clothingItem.description, clothingItem.material, clothingItem.size, clothingItem.price);
        clothingItemRepository.insert(clothingItemEntity);
        return "Item added successfully";
    }

}
