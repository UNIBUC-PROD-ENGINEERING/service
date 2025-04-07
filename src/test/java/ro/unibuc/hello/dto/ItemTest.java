package ro.unibuc.hello.dto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.unibuc.hello.data.Category;

class ItemTest {

    private Item item;

    @BeforeEach
    void setUp() {
        item = new Item("1", "Test Item", "Description", 100.0, LocalDateTime.now().plusDays(1), true, "test@example.com", null);
    }

    @Test
    void testGettersAndSetters() {
        item.setId("2");
        assertEquals("2", item.getId());

        item.setName("Updated Item");
        assertEquals("Updated Item", item.getName());

        item.setDescription("New Description");
        assertEquals("New Description", item.getDescription());

        item.setInitialPrice(200.0);
        assertEquals(200.0, item.getInitialPrice());

        LocalDateTime newEndTime = LocalDateTime.now().plusDays(2);
        item.setEndTime(newEndTime);
        assertEquals(newEndTime, item.getEndTime());

        item.setActive(false);
        assertFalse(item.isActive());

        item.setCreator("new_creator@example.com");
        assertEquals("new_creator@example.com", item.getCreator());
    }

    @Test
    void testHighestBidAndBidder() {
        item.setHighestBid(150.0);
        assertEquals(150.0, item.getHighestBid());

        item.setHighestBidder("bidder@example.com");
        assertEquals("bidder@example.com", item.getHighestBidder());
    }

    @Test
    void testCategorySetterAndGetter() {
        Category category = Category.ELECTRONICS;
        item.setCategory(category);
        assertEquals(category, item.getCategory());
    }
}
