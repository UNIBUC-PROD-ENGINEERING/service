package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InventoryDTOTest {

    InventoryDTO inventory = new InventoryDTO("1", "item1", 100, 10);

    @Test
    void testConstructor() {
        InventoryDTO inventoryDTO = new InventoryDTO("3", "item3", 150, 30);
        Assertions.assertEquals("3", inventoryDTO.getItemId());
        Assertions.assertEquals("item3", inventoryDTO.getName());
        Assertions.assertEquals(150, inventoryDTO.getStock());
        Assertions.assertEquals(30, inventoryDTO.getThreshold());
    }

    @Test
    void test_getId() {
        Assertions.assertEquals("1", inventory.getItemId());
    }

    @Test
    void test_setId() {
        inventory.setItemId("2");
        Assertions.assertEquals("2", inventory.getItemId());
    }

    @Test
    void test_getName() {
        Assertions.assertEquals("item1", inventory.getName());
    }

    @Test
    void test_setName() {
        inventory.setName("item2");
        Assertions.assertEquals("item2", inventory.getName());
    }

    @Test
    void test_getStock() {
        Assertions.assertEquals(100, inventory.getStock());
    }

    @Test
    void test_setStock() {
        inventory.setStock(200);
        Assertions.assertEquals(200, inventory.getStock());
    }

    @Test
    void test_getThreshold() {
        Assertions.assertEquals(10, inventory.getThreshold());
    }

    @Test
    void test_setThreshold() {
        inventory.setThreshold(20);
        Assertions.assertEquals(20, inventory.getThreshold());
    }
}
