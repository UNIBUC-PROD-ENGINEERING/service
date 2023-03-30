package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.RestaurantDTO;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Tag("IT")
public class RestaurantServiceTestIT {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RestaurantService restaurantService;

    private List<OrderEntity> orders;

    @Test
    public void testGetRestaurants() {
        restaurantRepository.deleteAll();
        List<RestaurantDTO> restaurants = restaurantService.getRestaurants();
        Assertions.assertNotNull(restaurants);
        Assertions.assertEquals(0, restaurants.size());
    }

    @Test
    public void testInsertRestaurant() {
        createObjects();

        List<String> orderIds = Arrays.asList(orders.get(0).getId().toString(), orders.get(1).getId().toString());

        RestaurantDTO restaurantDTO = restaurantService.insertRestaurant("Zest Pizza", "zest@gmail.com", "Sectorul 6 Bucuresti", orderIds);
        RestaurantDTO restaurantInsertedDTO = restaurantService.getRestaurant(restaurantDTO.getId());

        Assertions.assertEquals("Zest Pizza", restaurantDTO.getName());
        Assertions.assertEquals("zest@gmail.com", restaurantDTO.getEmail());
        Assertions.assertEquals("Sectorul 6 Bucuresti", restaurantDTO.getAddress());

        Assertions.assertEquals("Zest Pizza", restaurantDTO.getName());
        Assertions.assertEquals("zest@gmail.com", restaurantDTO.getEmail());
        Assertions.assertEquals("Sectorul 6 Bucuresti", restaurantDTO.getAddress());
    }

    @Test
    public void testUpdateRestaurant() {
        createObjects();

        List<String> orderIds = Arrays.asList(orders.get(0).getId().toString(), orders.get(1).getId().toString());

        RestaurantDTO restaurantDTO = restaurantService.insertRestaurant("Tudy's Pizza", "tudy@gmail.com", "Calea Victoriei nr.54", orderIds);

        this.orders = Arrays.asList(orderRepository.save(new OrderEntity()), orderRepository.save(new OrderEntity()));
        orderIds = Arrays.asList(orders.get(0).getId().toString(), orders.get(1).getId().toString());

        RestaurantDTO updatedRestaurantDTO = restaurantService.updateRestaurant(restaurantDTO.getId().toString(), restaurantDTO.getName().toString(),
                restaurantDTO.getEmail().toString(), restaurantDTO.getAddress().toString(), orderIds);
        RestaurantDTO restaurantUpdatedDTO = restaurantService.getRestaurant(updatedRestaurantDTO.getId());

        Assertions.assertEquals("Tudy's Pizza", restaurantDTO.getName());
        Assertions.assertEquals("tudy@gmail.com", restaurantDTO.getEmail());
        Assertions.assertEquals("Calea Victoriei nr.54", restaurantDTO.getAddress());

        Assertions.assertEquals("Tudy's Pizza", restaurantUpdatedDTO.getName());
        Assertions.assertEquals("tudy@gmail.com", restaurantUpdatedDTO.getEmail());
        Assertions.assertEquals("Calea Victoriei nr.54", restaurantUpdatedDTO.getAddress());
    }


    @Test()
    public void testDeleteClient(){
        restaurantRepository.deleteAll();

        createObjects();

        List<String> orderIds = Arrays.asList(orders.get(0).getId().toString(), orders.get(1).getId().toString());

        RestaurantDTO clientDTO = restaurantService.insertRestaurant("Dodo Pizza", "dodo@gmail.com", "Republica 24", orderIds);

        String deleteMessage = restaurantService.deleteRestaurant(clientDTO.getId().toString());

        Assertions.assertEquals("Restaurant with id " + clientDTO.getId().toString() + " was deleted", deleteMessage);

        Assertions.assertNull(restaurantService.getRestaurant(clientDTO.getId()));

    }


    public void createObjects(){
        OrderEntity order1 = new OrderEntity();
        OrderEntity order2 = new OrderEntity();

        this.orders =  Arrays.asList(orderRepository.save(order1), orderRepository.save(order2));

    }
}
