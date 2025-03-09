// package ro.unibuc.hello.controller;
 
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import ro.unibuc.hello.dto.Order;
// import ro.unibuc.hello.exception.EntityNotFoundException;
// import ro.unibuc.hello.service.OrdersService;

// import java.util.Arrays;
// import java.util.List;

// import static org.junit.Assert.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.*;
// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @Disabled
// class OrdersControllerTest {

//     @Mock
//     private OrdersService ordersService;

//     @InjectMocks
//     private OrdersController ordersController;

//     private MockMvc mockMvc;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);
//         mockMvc = MockMvcBuilders.standaloneSetup(ordersController).build();
//     }

//     @Test
//     void test_sayHello() throws Exception {
//         // Arrange
//         Order greeting = new Order("1", "Hello, there!");
//         when(ordersService.hello("there")).thenReturn(greeting);
    
//         // Act & Assert
//         mockMvc.perform(get("/hello-world?name=there"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value("1"))
//                .andExpect(jsonPath("$.content").value("Hello, there!"));
//     }
    

//     @Test
//     void test_info() throws Exception {
//         // Arrange
//         Order greeting = new Order("1", "there : some description");
//         when(ordersService.buildOrderFromInfo("there")).thenReturn(greeting);
    
//         // Act & Assert
//         mockMvc.perform(get("/info?title=there"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value("1"))
//                .andExpect(jsonPath("$.content").value("there : some description"));
//     }    

//     @Test
//     void test_info_cascadesException() {
//         // Arrange
//         String title = "there";
//         when(ordersService.buildOrderFromInfo(title)).thenThrow(new EntityNotFoundException(title));

//         // Act
//         EntityNotFoundException exception = assertThrows(
//                 EntityNotFoundException.class,
//                 () -> ordersController.info(title),
//                 "Expected info() to throw EntityNotFoundException, but it didn't");

//         // Assert
//         assertTrue(exception.getMessage().contains(title));
//     }

//     @Test
//     void test_getAllOrders() throws Exception {
//         // Arrange
//         List<Order> orders = Arrays.asList(new Order("1", "Hello"), new Order("2", "Hi"));
//         when(ordersService.getAllOrders()).thenReturn(orders);

//         // Act & Assert
//         mockMvc.perform(get("/orders"))
//             .andExpect(status().isOk())
//             .andExpect(jsonPath("$[0].id").value("1"))
//             .andExpect(jsonPath("$[0].content").value("Hello"))
//             .andExpect(jsonPath("$[1].id").value("2"))
//             .andExpect(jsonPath("$[1].content").value("Hi"));
//     }

//     @Test
//     void test_createOrder() throws Exception {
//         // Arrange
//         Order newOrder = new Order("1", "Hello, World!");
//         when(ordersService.saveOrder(any(Order.class))).thenReturn(newOrder);
    
//         // Act & Assert
//         mockMvc.perform(post("/orders")
//                .content("{\"content\":\"Hello, World!\"}")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value("1"))
//                .andExpect(jsonPath("$.content").value("Hello, World!"));
//     }
    
//     @Test
//     void test_updateOrder() throws Exception {
//         // Arrange
//         String id = "1";
//         Order updatedOrder = new Order(id, "Updated Order");
//         when(ordersService.updateOrder(eq(id), any(Order.class))).thenReturn(updatedOrder);
    
//         // Act & Assert
//         mockMvc.perform(put("/orders/{id}", id)
//                .content("{\"content\":\"Updated Order\"}")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value("1"))
//                .andExpect(jsonPath("$.content").value("Updated Order"));
//     }
    
//     @Test
//     void test_deleteOrder() throws Exception {
//         String id = "1";
//         Order greeting = new Order(id, "Hello");
//         when(ordersService.saveOrder(any(Order.class))).thenReturn(greeting);
    
//         // add greeting
//         mockMvc.perform(post("/orders")
//                .content("{\"content\":\"Hello\"}")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
    
//         // delete greeting
//         mockMvc.perform(delete("/orders/{id}", id))
//                .andExpect(status().isOk());
    
//         verify(ordersService, times(1)).deleteOrder(id);
    
//         // check if greeting is deleted
//         mockMvc.perform(get("/orders"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isEmpty());
//     }
    
// }
