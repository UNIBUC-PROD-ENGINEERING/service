package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.InventoryEntity;
import ro.unibuc.hello.data.InventoryRepository;
import ro.unibuc.hello.dto.InventoryDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<InventoryDTO> getAllInventoryItems() {
        List<InventoryEntity> entities = inventoryRepository.findAll();
        return entities.stream()
                .map(entity -> new InventoryDTO(entity.getId(), entity.getName(), entity.getStock(), entity.getThreshold()))
                .collect(Collectors.toList());
    }

    public InventoryDTO getInventoryItemById(String id) throws EntityNotFoundException {
        InventoryEntity entity = inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory item with ID " + id + " not found"));
        return new InventoryDTO(entity.getId(), entity.getName(), entity.getStock(), entity.getThreshold());
    }

    public InventoryDTO createInventoryItem(InventoryDTO inventoryDTO) {
        InventoryEntity inventoryItem = new InventoryEntity(
                inventoryDTO.getName(),
                inventoryDTO.getStock(),
                inventoryDTO.getThreshold()
        );
        inventoryRepository.save(inventoryItem);
        return new InventoryDTO(inventoryItem.getId(), inventoryItem.getName(), inventoryItem.getStock(), inventoryItem.getThreshold());
    }

    public InventoryDTO updateInventoryStock(String id, Integer stock) throws EntityNotFoundException {
        InventoryEntity inventoryItem = inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory item with ID " + id + " not found"));
        inventoryItem.setStock(stock);
        inventoryRepository.save(inventoryItem);
        return new InventoryDTO(inventoryItem.getId(), inventoryItem.getName(), inventoryItem.getStock(), inventoryItem.getThreshold());
    }

    public void deleteInventoryItem(String id) throws EntityNotFoundException {
        InventoryEntity inventoryItem = inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory item with ID " + id + " not found"));
        inventoryRepository.delete(inventoryItem);
    }
}
