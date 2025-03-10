package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InventoryDTOTest {

    InventoryDTO inventory = new InventoryDTO("1", "item1", 100, 10);

    @Test
    void test_getId() {
        Assertions.assertEquals("1", inventory.getId());
    }

    @Test
    void test_setId() {
        inventory.setId("2");
        Assertions.assertEquals("2", inventory.getId());
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
