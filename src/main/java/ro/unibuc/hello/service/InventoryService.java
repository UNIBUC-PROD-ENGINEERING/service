package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.InventoryEntity;
import ro.unibuc.hello.data.InventoryRepository;
import ro.unibuc.hello.dto.InventoryDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.ValidationException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<InventoryDTO> getAllInventoryItems() {
        List<InventoryEntity> entities = inventoryRepository.findAll();
        return entities.stream()
                .map(entity -> new InventoryDTO(entity.getItemId(), entity.getName(), entity.getStock(), entity.getThreshold()))
                .collect(Collectors.toList());
    }

    public InventoryDTO getInventoryItemById(String id) throws EntityNotFoundException {
        InventoryEntity entity = inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory item with ID " + id + " not found"));
        return new InventoryDTO(entity.getItemId(), entity.getName(), entity.getStock(), entity.getThreshold());
    }

    public InventoryDTO createInventoryItem(InventoryDTO inventoryDTO) {
        validateInventoryItem(inventoryDTO);

        InventoryEntity inventoryItem = new InventoryEntity(
            inventoryDTO.getItemId(),
            inventoryDTO.getName(),
            inventoryDTO.getStock() != null ? inventoryDTO.getStock() : 0,
            inventoryDTO.getThreshold() != null ? inventoryDTO.getThreshold() : 0
        );
        inventoryRepository.save(inventoryItem);
        return new InventoryDTO(inventoryItem.getItemId(), inventoryItem.getName(), inventoryItem.getStock(), inventoryItem.getThreshold());
    }

    public InventoryDTO updateInventoryStock(String id, Integer stock) throws EntityNotFoundException {
        if (stock < 0) {
            throw new ValidationException("Stock cannot be negative");
        }

        InventoryEntity inventoryItem = inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory item with ID " + id + " not found"));
        inventoryItem.setStock(stock);
        inventoryRepository.save(inventoryItem);
        return new InventoryDTO(inventoryItem.getItemId(), inventoryItem.getName(), inventoryItem.getStock(), inventoryItem.getThreshold());
    }

    public void deleteInventoryItem(String id) throws EntityNotFoundException {
        InventoryEntity inventoryItem = inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory item with ID " + id + " not found"));
        inventoryRepository.delete(inventoryItem);
    }

    private void validateInventoryItem(InventoryDTO inventoryDTO) {
        if (inventoryDTO.getName() == null || inventoryDTO.getName().isEmpty()) {
            throw new ValidationException("Item name cannot be null or empty");
        }

        if (inventoryDTO.getName().length() > 100) {
            throw new ValidationException("Item name cannot exceed 100 characters");
        }

        if (inventoryDTO.getStock() != null && inventoryDTO.getStock() < 0) {
            throw new ValidationException("Stock cannot be negative");
        }

        if (inventoryDTO.getStock() != null && inventoryDTO.getStock() > 100000) {
            throw new ValidationException("Stock cannot exceed 100,000 units");
        }

        if (inventoryDTO.getThreshold() != null && inventoryDTO.getThreshold() < 0) {
            throw new ValidationException("Threshold cannot be negative");
        }

        if (inventoryDTO.getThreshold() != null && inventoryDTO.getThreshold() > 1000) {
            throw new ValidationException("Threshold cannot exceed 1,000 units");
        }

        if (inventoryRepository.existsById(inventoryDTO.getItemId())) {
            throw new ValidationException("Item ID must be unique");
        }
    }
}
