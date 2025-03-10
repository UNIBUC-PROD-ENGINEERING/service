package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.InventoryDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<InventoryDTO> getAllInventoryItems() {
        return inventoryService.getAllInventoryItems();
    }

    @GetMapping("/{id}")
    public InventoryDTO getInventoryItemById(@PathVariable String id) throws EntityNotFoundException {
        return inventoryService.getInventoryItemById(id);
    }

    @PostMapping
    public InventoryDTO createInventoryItem(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.createInventoryItem(inventoryDTO);
    }

    @PutMapping("/{id}/stock")
    public InventoryDTO updateInventoryStock(@PathVariable String id, @RequestParam Integer stock) throws EntityNotFoundException {
        return inventoryService.updateInventoryStock(id, stock);
    }

    @DeleteMapping("/{id}")
    public void deleteInventoryItem(@PathVariable String id) throws EntityNotFoundException {
        inventoryService.deleteInventoryItem(id);
    }
}
