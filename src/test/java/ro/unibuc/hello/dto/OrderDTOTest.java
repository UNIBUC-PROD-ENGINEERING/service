package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ro.unibuc.hello.data.DishesEntity;
import ro.unibuc.hello.data.RestaurantEntity;
import ro.unibuc.hello.data.OrderEntity;
import ro.unibuc.hello.data.ClientEntity;

import java.util.ArrayList;
import java.util.List;


public class OrderDTOTest {
    @Test
    public void testConstructor() {
        ClientEntity mockClient = Mockito.mock(ClientEntity.class);
        Mockito.when(mockClient.getName()).thenReturn("Dan");

        RestaurantEntity mockRestaurant = Mockito.mock(RestaurantEntity.class);
        Mockito.when(mockRestaurant.getName()).thenReturn("Maestro");

        List<DishesEntity> mockDishesEntities = new ArrayList<>();
        DishesEntity mockDish1 = Mockito.mock(DishesEntity.class);
        Mockito.when(mockDish1.getName()).thenReturn("Pizza Margherita");
        DishesEntity mockDish2 = Mockito.mock(DishesEntity.class);
        Mockito.when(mockDish2.getName()).thenReturn("Pizza Carnivora");
        mockDishesEntities.add(mockDish1);
        mockDishesEntities.add(mockDish2);

        OrderEntity mockOrder = Mockito.mock(OrderEntity.class);
        Mockito.when(mockOrder.getId()).thenReturn("1");
        Mockito.when(mockOrder.getClient()).thenReturn(mockClient);
        Mockito.when(mockOrder.getRestaurant()).thenReturn(mockRestaurant);
        Mockito.when(mockOrder.getDishes()).thenReturn((ArrayList<DishesEntity>) mockDishesEntities);

        OrderDTO orderDTO = new OrderDTO(mockOrder);

        Assertions.assertEquals("1", orderDTO.getId());
        Assertions.assertEquals("Dan", orderDTO.getUser().getName());
        Assertions.assertEquals("Maestro", orderDTO.getRestaurant().getName());
        Assertions.assertEquals("Pizza Margherita", orderDTO.getDishes().get(0).getName());
        Assertions.assertEquals("Pizza Carnivora", orderDTO.getDishes().get(1).getName());
    }

    @Test
    public void testGetId() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId("1");
        Assertions.assertEquals("1", orderDTO.getId());
    }

    @Test
    public void testGetUser() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("John");
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUser(clientDTO);
        Assertions.assertEquals("John", orderDTO.getUser().getName());
    }

    @Test
    public void testSetRestaurant() {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName("Pizza Place");
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setRestaurant(restaurantDTO);
        Assertions.assertEquals("Pizza Place", orderDTO.getRestaurant().getName());
    }

    @Test
    public void testSetDishes() {
        DishesEntity mockDish1 = Mockito.mock(DishesEntity.class);
        Mockito.when(mockDish1.getName()).thenReturn("Pizza Margherita");

        ArrayList<DishesDTO> dishesDTO = new ArrayList<DishesDTO>();
        DishesDTO dish = new DishesDTO(mockDish1);
        dishesDTO.add(dish);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDishes(dishesDTO);
        Assertions.assertEquals("Pizza Margherita", orderDTO.getDishes().get(0).getName());
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
        Assertions.assertEquals("Order[user='John', restaurant='Pizza Place', dishes:\n'1']", orderDTO.toString());
    }
}
