package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.OrderStatus; // Import OrderStatus
import ro.unibuc.hello.dto.OrderDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.OrderService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testGetAllOrders() throws Exception {
        // Arrange
        List<OrderDTO> orders = Arrays.asList(
                new OrderDTO("1", "worker1", OrderStatus.PENDING, "item1", 10, "location1"),
                new OrderDTO("2", "worker2", OrderStatus.COMPLETED, "item2", 20, "location2")
        );
        when(orderService.getAllOrders()).thenReturn(orders);

        // Act & Assert
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].robotId").value("worker1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].robotId").value("worker2"));
    }

    @Test
    void testGetOrderById_ExistingEntity() throws Exception {
        // Arrange
        String id = "1";
        OrderDTO order = new OrderDTO(id, "worker1", OrderStatus.PENDING, "item1", 10, "location1");
        when(orderService.getOrderById(id)).thenReturn(order);

        // Act & Assert
        mockMvc.perform(get("/orders/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.robotId").value("worker1"));
    }

    @Test
    void testCreateOrder() throws Exception {
        // Arrange
        OrderDTO newOrder = new OrderDTO(null, "worker1", OrderStatus.PENDING, "item1", 10, "location1");
        OrderDTO createdOrder = new OrderDTO("1", "worker1", OrderStatus.PENDING, "item1", 10, "location1");
        when(orderService.createOrder(any(OrderDTO.class))).thenReturn(createdOrder);

        // Act & Assert
        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"robotId\":\"worker1\",\"status\":\"PENDING\",\"itemId\":\"item1\",\"quantity\":10,\"location\":\"location1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.robotId").value("worker1"));
    }

    @Test
    void testUpdateOrderStatus_ExistingEntity() throws Exception {
        String id = "1";
        String status = "COMPLETED";
        OrderDTO updatedOrder = new OrderDTO(id, "worker1", OrderStatus.COMPLETED, "item1", 10, "location1");
        when(orderService.updateOrderStatus(eq(id), eq(status))).thenReturn(updatedOrder);
    
        mockMvc.perform(put("/orders/{id}/status", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"COMPLETED\"}")) 
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }
    

    @Test
    void testDeleteOrder_ExistingEntity() throws Exception {
        // Arrange
        String id = "1";
        doNothing().when(orderService).deleteOrder(id);

        // Act & Assert
        mockMvc.perform(delete("/orders/{id}", id))
                .andExpect(status().isOk());

        verify(orderService, times(1)).deleteOrder(id);
    }
}