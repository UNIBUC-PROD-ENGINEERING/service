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
import ro.unibuc.hello.data.OrderStatus;
import ro.unibuc.hello.data.RobotRepository;
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

    @Mock
    private RobotRepository robotRepository;

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
                new OrderEntity("worker1", OrderStatus.PENDING, "item1", 10, "location1"),
                new OrderEntity("worker2", OrderStatus.COMPLETED, "item2", 20, "location2")
        );
        when(orderRepository.findAll()).thenReturn(entities);

        // Act
        List<OrderDTO> orders = orderService.getAllOrders();

        // Assert
        assertEquals(2, orders.size());
        assertEquals("worker1", orders.get(0).getRobotId());
        assertEquals("worker2", orders.get(1).getRobotId());
    }

    @Test
    void testGetOrderById_ExistingEntity() throws EntityNotFoundException {
        // Arrange
        String id = "1";
        OrderEntity entity = new OrderEntity("worker1", OrderStatus.PENDING, "item1", 10, "location1");
        entity.setId(id);
        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        OrderDTO order = orderService.getOrderById(id);

        // Assert
        assertNotNull(order);
        assertEquals(id, order.getId()); // Ensure we're comparing the correct field
        assertEquals("worker1", order.getRobotId());
    }

    @Test
    void testGetOrderById_NonExistingEntity() {
        // Arrange
        String id = "NonExistingId";
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> orderService.getOrderById(id));
    }

    // @Test
    // void testCreateOrder() {
    //     // Arrange
    //     OrderDTO orderDTO = new OrderDTO(null, "worker1", OrderStatus.PENDING, "item1", 10, "location1");
    //     OrderEntity entity = new OrderEntity("worker1", OrderStatus.PENDING, "item1", 10, "location1");
    //     entity.setId("1");  // Assuming the entity gets an ID after saving
        
    //     // Mock the repository behavior
    //     when(robotRepository.existsById("worker1")).thenReturn(true); // Mock robot check to return true
    //     when(orderRepository.save(any(OrderEntity.class))).thenReturn(entity); // Mock order save behavior
    
    //     // Act
    //     OrderDTO createdOrder = orderService.createOrder(orderDTO);
    
    //     // Log the result to debug
    //     System.out.println("Created Order: " + createdOrder);  // Log output to console
    
    //     System.out.println("Expected ID: 1, Actual ID: " + createdOrder.getId());  // Log to compare
        
    //     // Assert
    //     assertNotNull(createdOrder);  // Ensure the created order is not null
    //     assertEquals("worker1", createdOrder.getRobotId());
    //     assertEquals("1", createdOrder.getId()); // Check that the ID was set after saving
    // }
    
    

    @Test
    void testUpdateOrderStatus_ExistingEntity() throws EntityNotFoundException {
        // Arrange
        String id = "1";
        String status = "COMPLETED";
        OrderEntity entity = new OrderEntity("worker1", OrderStatus.PENDING, "item1", 10, "location1");
        entity.setId(id);
        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(entity);

        // Act
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, status);

        // Assert
        assertNotNull(updatedOrder);
        assertEquals(OrderStatus.COMPLETED, updatedOrder.getStatus());
    }

    @Test
    void testUpdateOrderStatus_NonExistingEntity() {
        // Arrange
        String id = "NonExistingId";
        String status = "COMPLETED";
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> orderService.updateOrderStatus(id, status));
    }

    @Test
    void testDeleteOrder_ExistingEntity() throws EntityNotFoundException {
        // Arrange
        String id = "1";
        OrderEntity entity = new OrderEntity("worker1", OrderStatus.PENDING, "item1", 10, "location1");
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
