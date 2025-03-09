package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.data.OrderEntity;
import ro.unibuc.hello.data.OrderRepository;
import ro.unibuc.hello.dto.OrderDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllOrders() {
        // Arrange
        List<OrderEntity> entities = Arrays.asList(
                new OrderEntity("worker1", "pending", "item1", 10, "location1"),
                new OrderEntity("worker2", "completed", "item2", 20, "location2")
        );
        when(orderRepository.findAll()).thenReturn(entities);

        // Act
        List<OrderDTO> orders = orderService.getAllOrders();

        // Assert
        assertEquals(2, orders.size());
        assertEquals("worker1", orders.get(0).getWorkerId());
        assertEquals("worker2", orders.get(1).getWorkerId());
    }

    @Test
    void testGetOrderById_ExistingEntity() throws EntityNotFoundException {
        // Arrange
        String id = "1";
        OrderEntity entity = new OrderEntity("worker1", "pending", "item1", 10, "location1");
        entity.setId(id);
        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        OrderDTO order = orderService.getOrderById(id);

        // Assert
        assertNotNull(order);
        assertEquals(id, order.getId());
        assertEquals("worker1", order.getWorkerId());
    }

    @Test
    void testGetOrderById_NonExistingEntity() {
        // Arrange
        String id = "NonExistingId";
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> orderService.getOrderById(id));
    }

    @Test
    void testCreateOrder() {
        // Arrange
        OrderDTO orderDTO = new OrderDTO(null, "worker1", "pending", "item1", 10, "location1");
        OrderEntity entity = new OrderEntity("worker1", "pending", "item1", 10, "location1");
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(entity);

        // Act
        OrderDTO createdOrder = orderService.createOrder(orderDTO);

        // Assert
        assertNotNull(createdOrder);
        assertEquals("worker1", createdOrder.getWorkerId());
    }

    @Test
    void testUpdateOrderStatus_ExistingEntity() throws EntityNotFoundException {
        // Arrange
        String id = "1";
        String status = "completed";
        OrderEntity entity = new OrderEntity("worker1", "pending", "item1", 10, "location1");
        entity.setId(id);
        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(entity);

        // Act
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, status);

        // Assert
        assertNotNull(updatedOrder);
        assertEquals(status, updatedOrder.getStatus());
    }

    @Test
    void testUpdateOrderStatus_NonExistingEntity() {
        // Arrange
        String id = "NonExistingId";
        String status = "completed";
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> orderService.updateOrderStatus(id, status));
    }

    @Test
    void testDeleteOrder_ExistingEntity() throws EntityNotFoundException {
        // Arrange
        String id = "1";
        OrderEntity entity = new OrderEntity("worker1", "pending", "item1", 10, "location1");
        entity.setId(id);
        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        orderService.deleteOrder(id);

        // Assert
        verify(orderRepository, times(1)).delete(entity);
    }

    @Test
    void testDeleteOrder_NonExistingEntity() {
        // Arrange
        String id = "NonExistingId";
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> orderService.deleteOrder(id));
    }
}