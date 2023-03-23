package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.unibuc.hello.data.OrderEntity;


public class OrderDTOTest {

    @Test
    public void testGetId() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId("1");
        assertEquals("1", orderDTO.getId());
    }

    @Test
    public void testGetUser() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("John");
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUser(clientDTO);
        assertEquals("John", orderDTO.getUser().getName());
    }

    @Test
    public void testSetRestaurant() {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName("Pizza Place");
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setRestaurant(restaurantDTO);
        assertEquals("Pizza Place", orderDTO.getRestaurant().getName());
    }

    @Test
    public void testToString() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("John");
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName("Pizza Place");
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId("1");
        orderDTO.setUser(clientDTO);
        orderDTO.setRestaurant(restaurantDTO);
        assertEquals("Order[user='John', restaurant='Pizza Place', dishes:\n'1']", orderDTO.toString());
    }
}
