package ro.unibuc.hello.dto;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import ro.unibuc.hello.data.*;

import java.util.*;

public class MenuDTOTest {

    @Test
    public void testConstructor() {
        RestaurantEntity mockRestaurant = Mockito.mock(RestaurantEntity.class);
        Mockito.when(mockRestaurant.getName()).thenReturn("Maestro");

        List<DishesEntity> mockDishesEntities = new ArrayList<>();
        DishesEntity mockDish1 = Mockito.mock(DishesEntity.class);
        Mockito.when(mockDish1.getName()).thenReturn("Pizza Margherita");
        DishesEntity mockDish2 = Mockito.mock(DishesEntity.class);
        Mockito.when(mockDish2.getName()).thenReturn("Pizza Carnivora");
        mockDishesEntities.add(mockDish1);
        mockDishesEntities.add(mockDish2);

        MenuEntity mockMenu = Mockito.mock(MenuEntity.class);
        Mockito.when(mockMenu.getId()).thenReturn("1");
        Mockito.when(mockMenu.getRestaurant()).thenReturn(mockRestaurant);
        Mockito.when(mockMenu.getDishes()).thenReturn((ArrayList<DishesEntity>) mockDishesEntities);

        MenuDTO menuDTO = new MenuDTO(mockMenu);

        Assertions.assertEquals("1", menuDTO.getId());
        Assertions.assertEquals("Maestro", menuDTO.getRestaurant().getName());
        Assertions.assertEquals("Pizza Margherita", menuDTO.getDishes().get(0).getName());
        Assertions.assertEquals("Pizza Carnivora", menuDTO.getDishes().get(1).getName());
    }


    @Test
    public void testToString() {
        // Create a sample MenuDTO
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId("123");

        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName("My Restaurant");
        menuDTO.setRestaurant(restaurantDTO);

        DishesDTO dishesDTO1 = new DishesDTO();
        dishesDTO1.setName("Dish 1");
        dishesDTO1.setPrice(10);

        DishesDTO dishesDTO2 = new DishesDTO();
        dishesDTO2.setName("Dish 2");
        dishesDTO2.setPrice(20);

        menuDTO.setDishes(Arrays.asList(dishesDTO1, dishesDTO2));

        // Verify that the toString() method returns the expected string
        assertEquals("Menu[restaurant='My Restaurant', dishes:\n'123']", menuDTO.toString());
    }
}
