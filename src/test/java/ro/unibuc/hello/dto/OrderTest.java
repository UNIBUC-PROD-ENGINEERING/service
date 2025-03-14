package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.unibuc.hello.data.OrderStatus; // Import OrderStatus

class OrderDTOTest {

    OrderDTO order = new OrderDTO("1", "worker1", OrderStatus.PENDING, "item1", 10, "location1");

    @Test
    void test_getId() {
        Assertions.assertEquals("1", order.getId());
    }

    @Test
    void test_setId() {
        order.setId("2");
        Assertions.assertEquals("2", order.getId());
    }

    @Test
    void test_getWorkerId() {
        Assertions.assertEquals("worker1", order.getRobotId());
    }

    @Test
    void test_setWorkerId() {
        order.setWorkerId("worker2");
        Assertions.assertEquals("worker2", order.getRobotId());
    }

    @Test
    void test_getStatus() {
        Assertions.assertEquals(OrderStatus.PENDING, order.getStatus());
    }

    @Test
    void test_setStatus() {
        order.setStatus(OrderStatus.COMPLETED);
        Assertions.assertEquals(OrderStatus.COMPLETED, order.getStatus());
    }

    @Test
    void test_getItemId() {
        Assertions.assertEquals("item1", order.getItemId());
    }

    @Test
    void test_setItemId() {
        order.setItemId("item2");
        Assertions.assertEquals("item2", order.getItemId());
    }

    @Test
    void test_getQuantity() {
        Assertions.assertEquals(10, order.getQuantity());
    }

    @Test
    void test_setQuantity() {
        order.setQuantity(20);
        Assertions.assertEquals(20, order.getQuantity());
    }

    @Test
    void test_getLocation() {
        Assertions.assertEquals("location1", order.getLocation());
    }

    @Test
    void test_setLocation() {
        order.setLocation("location2");
        Assertions.assertEquals("location2", order.getLocation());
    }
}