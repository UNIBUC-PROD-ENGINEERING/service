package ro.unibuc.hello.permissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.ItemRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.UnauthorizedException;

@Component
public class ItemPermissionChecker implements PermissionChecker<ItemEntity> {
    @Autowired
    public ItemRepository itemRepository;

    @Override
    public void checkOwnership(String userId, String resourceId) {
        ItemEntity item = itemRepository.findById(resourceId)
            .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        
        if (!item.getOwner().getId().equals(userId)) {
            throw new UnauthorizedException("You do not own this item");
        }
    }
}
