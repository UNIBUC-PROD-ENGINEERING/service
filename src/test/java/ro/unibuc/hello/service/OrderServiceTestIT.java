package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.DishesDTO;
import ro.unibuc.hello.dto.OrderDTO;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Tag("IT")
public class OrderServiceTestIT {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    DishesRepository dishesRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    private ClientEntity client;

    private RestaurantEntity restaurant;

    private List<DishesEntity> dishes;

    @Test
    public void testGetOrders() {
        orderRepository.deleteAll();
        List<OrderDTO> orders = orderService.getOrders();
        Assertions.assertNotNull(orders);
        Assertions.assertEquals(0, orders.size());
    }

    @Test
    public void testInsertOrder() {
        createObjects();

        List<String> dishesIds = Arrays.asList(dishes.get(0).getId().toString(), dishes.get(1).getId().toString());

        OrderDTO orderDTO = orderService.insertOrder(restaurant.getId().toString(), client.getId().toString(), dishesIds);

        OrderDTO orderInsertedDTO = orderService.getOrder(orderDTO.getId());

        // test that returned DTO is valid
        Assertions.assertNotNull(orderDTO);
        Assertions.assertEquals(restaurant.getName(), orderDTO.getRestaurant().getName());
        Assertions.assertEquals(client.getName(), orderDTO.getUser().getName());
        Assertions.assertEquals(dishes.size(), orderDTO.getDishes().size());
        Assertions.assertEquals(dishes.get(0).getName(), orderDTO.getDishes().get(0).getName());
        Assertions.assertEquals(dishes.get(1).getName(), orderDTO.getDishes().get(0).getName());

        // test that order inserted in db is valid
        Assertions.assertEquals(restaurant.getName(), orderInsertedDTO.getRestaurant().getName());
        Assertions.assertEquals(client.getName(), orderInsertedDTO.getUser().getName());
        Assertions.assertEquals(dishes.size(), orderInsertedDTO.getDishes().size());
        Assertions.assertEquals(dishes.get(0).getName(), orderInsertedDTO.getDishes().get(0).getName());
        Assertions.assertEquals(dishes.get(1).getName(), orderInsertedDTO.getDishes().get(0).getName());
    }

    @Test
    public void testUpdateOrder() {
        createObjects();

        List<String> dishesIds = Arrays.asList(dishes.get(0).getId().toString(), dishes.get(1).getId().toString());

        OrderDTO orderDTO = orderService.insertOrder(restaurant.getId().toString(), client.getId().toString(), dishesIds);

        this.dishes = Arrays.asList(dishesRepository.save(new DishesEntity()),dishesRepository.save(new DishesEntity()));
        dishesIds = Arrays.asList(dishes.get(0).getId().toString(), dishes.get(1).getId().toString());

        OrderDTO updatedOrderDTO = orderService.updateOrder(orderDTO.getId().toString(),
                restaurant.getId().toString(), client.getId().toString(), dishesIds);

        OrderDTO orderUpdatedDTO = orderService.getOrder(updatedOrderDTO.getId());

        // test that updated returned dto is valid
        Assertions.assertNotNull(updatedOrderDTO);
        Assertions.assertEquals(restaurant.getName(), updatedOrderDTO.getRestaurant().getName());
        Assertions.assertEquals(client.getName(), updatedOrderDTO.getUser().getName());
        Assertions.assertEquals(dishes.size(), updatedOrderDTO.getDishes().size());
        Assertions.assertEquals(dishes.get(0).getName(), updatedOrderDTO.getDishes().get(0).getName());
        Assertions.assertEquals(dishes.get(1).getName(), updatedOrderDTO.getDishes().get(0).getName());

        // test that the updated order in db is valid
        Assertions.assertEquals(restaurant.getName(), orderUpdatedDTO.getRestaurant().getName());
        Assertions.assertEquals(client.getName(), orderUpdatedDTO.getUser().getName());
        Assertions.assertEquals(dishes.size(), orderUpdatedDTO.getDishes().size());
        Assertions.assertEquals(dishes.get(0).getName(), orderUpdatedDTO.getDishes().get(0).getName());
        Assertions.assertEquals(dishes.get(1).getName(), orderUpdatedDTO.getDishes().get(0).getName());
    }

    @Test
    public void testDeleteOrder() {
        orderRepository.deleteAll();

        createObjects();

        List<String> dishesIds = Arrays.asList(dishes.get(0).getId().toString(), dishes.get(1).getId().toString());

        OrderDTO orderDTO = orderService.insertOrder(restaurant.getId().toString(),
                client.getId().toString(), dishesIds);

        String deleteMessage = orderService.deleteOrder(orderDTO.getId().toString());

        Assertions.assertEquals("Order with id:" + orderDTO.getId().toString() + "was deleted", deleteMessage);

        Assertions.assertNull(orderService.getOrder(orderDTO.getId()));
    }

    private void createObjects() {
        ClientEntity client = new ClientEntity("Radu", "radu@gmail.com", "PLopilor 22");
        RestaurantEntity restaurant = new RestaurantEntity("IrishPub", "irish.pub@gmail.com", "1 Decembrie, nr. 23");
        DishesEntity dish1 = new DishesEntity();
        DishesEntity dish2 = new DishesEntity();

        this.client = clientRepository.save(client);
        this.restaurant = restaurantRepository.save(restaurant);
        this.dishes = Arrays.asList(dishesRepository.save(dish1), dishesRepository.save(dish2));
    }
}
