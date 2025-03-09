// package ro.unibuc.hello.service;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import ro.unibuc.hello.data.InformationEntity;
// import ro.unibuc.hello.data.InformationRepository;
// import ro.unibuc.hello.dto.Order;
// import ro.unibuc.hello.exception.EntityNotFoundException;
// import java.util.Arrays;
// import java.util.List;
// import java.util.Optional;
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// @Disabled
// @ExtendWith(SpringExtension.class)
// class OrdersServiceTest {

//     @Mock
//     private InformationRepository informationRepository;

//     @InjectMocks
//     private OrdersService ordersService = new OrdersService();

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     void testHello() {
//         // Arrange
//         String name = "John";

//         // Act
//         Order greeting = ordersService.hello(name);

//         // Assert
//         assertNotNull(greeting);
//         assertEquals("Hello, John!", greeting.getContent());
//     }

//     @Test
//     void testBuildOrderFromInfo_ExistingEntity() throws EntityNotFoundException {
//         // Arrange
//         String title = "Title";
//         String description = "Description";
//         InformationEntity entity = new InformationEntity(title, description);
//         when(informationRepository.findByTitle(title)).thenReturn(entity);

//         // Act
//         Order greeting = ordersService.buildOrderFromInfo(title);

//         // Assert
//         assertNotNull(greeting);
//         assertEquals("Title : Description!", greeting.getContent());
//     }

//     @Test
//     void testBuildOrderFromInfo_NonExistingEntity() {
//         // Arrange
//         String title = "NonExistingTitle";
//         when(informationRepository.findByTitle(title)).thenReturn(null);

//         // Act & Assert
//         assertThrows(EntityNotFoundException.class, () -> ordersService.buildOrderFromInfo(title));
//     }

//     @Test
//     void testSaveOrder() {
//         // Arrange
//         Order greeting = new Order("1", "Hello");

//         // Act
//         when(informationRepository.save(any(InformationEntity.class))).thenReturn(new InformationEntity("1", "Hello", null));
//         Order savedOrder = ordersService.saveOrder(greeting);

//         // Assert
//         assertNotNull(savedOrder);
//         assertEquals("1", savedOrder.getId());
//         assertEquals("Hello", savedOrder.getContent());
//     }

//     @Test
//     void testUpdateOrder_ExistingEntity() throws EntityNotFoundException {
//         // Arrange
//         String id = "1";
//         Order greeting = new Order(id, "Updated Order");
//         InformationEntity entity = new InformationEntity(id, "Old Order", "Description");
//         when(informationRepository.findById(id)).thenReturn(Optional.of(entity));
//         when(informationRepository.save(any(InformationEntity.class))).thenReturn(new InformationEntity(id, "Updated Order", "Description"));

//         // Act
//         Order updatedOrder = ordersService.updateOrder(id, greeting);

//         // Assert
//         assertNotNull(updatedOrder);
//         assertEquals(id, updatedOrder.getId());
//         assertEquals("Updated Order", updatedOrder.getContent());
//     }

//     @Test
//     void testUpdateOrder_NonExistingEntity() {
//         // Arrange
//         String id = "NonExistingId";
//         Order greeting = new Order(id, "Updated Order");
//         when(informationRepository.findById(id)).thenReturn(Optional.empty());

//         // Act & Assert
//         assertThrows(EntityNotFoundException.class, () -> ordersService.updateOrder(id, greeting));
//     }

//     @Test
//     void testDeleteOrder_ExistingEntity() throws EntityNotFoundException {
//         // Arrange
//         String id = "1";
//         InformationEntity entity = new InformationEntity(id, "Order to delete", "Description");
//         when(informationRepository.findById(id)).thenReturn(Optional.of(entity));

//         // Act
//         ordersService.deleteOrder(id);

//         // Assert
//         verify(informationRepository, times(1)).delete(entity);
//     }

//     @Test
//     void testDeleteOrder_NonExistingEntity() {
//         // Arrange
//         String id = "NonExistingId";
//         when(informationRepository.findById(id)).thenReturn(Optional.empty());

//         // Act & Assert
//         assertThrows(EntityNotFoundException.class, () -> ordersService.deleteOrder(id));
//     }

//     @Test
//     void testGetAllOrders() {
//         // Arrange
//         List<InformationEntity> entities = Arrays.asList(
//                 new InformationEntity("1", "Order 1", "Description 1"),
//                 new InformationEntity("2", "Order 2", "Description 2")
//         );
//         when(informationRepository.findAll()).thenReturn(entities);

//         // Act
//         List<Order> orders = ordersService.getAllOrders();

//         // Assert
//         assertEquals(2, orders.size());
//         assertEquals("1", orders.get(0).getId());
//         assertEquals("Order 1", orders.get(0).getContent());
//         assertEquals("2", orders.get(1).getId());
//         assertEquals("Order 2", orders.get(1).getContent());
//     }
// }
