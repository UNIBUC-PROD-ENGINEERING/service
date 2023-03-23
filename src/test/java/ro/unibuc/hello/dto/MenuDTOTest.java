package ro.unibuc.hello.dto;

import org.junit.Test;
import static org.junit.Assert.*;
import ro.unibuc.hello.data.*;

import java.util.*;

public class MenuDTOTest {

    @Test
    public void testConstructor() {
        // Create a sample MenuEntity
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setId("123");

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setName("My Restaurant");

        DishesEntity dishesEntity1 = new DishesEntity();
        dishesEntity1.setName("Dish 1");
        dishesEntity1.setPrice(10);

        DishesEntity dishesEntity2 = new DishesEntity();
        dishesEntity2.setName("Dish 2");
        dishesEntity2.setPrice(20);

        menuEntity.setRestaurant(restaurantEntity);
        List<DishesEntity> dishesEntities = menuEntity.getDishes();
        ArrayList<DishesEntity> dishesList = new ArrayList<>(dishesEntities);
        dishesList.add(dishesEntity1);
        dishesList.add(dishesEntity1);
        menuEntity.setDishes(dishesList);

        // Create a new MenuDTO using the sample MenuEntity
        MenuDTO menuDTO = new MenuDTO(menuEntity);

        // Verify that the MenuDTO was created correctly
        assertEquals("123", menuDTO.getId());
        assertEquals("My Restaurant", menuDTO.getRestaurant().getName());
        assertEquals(2, menuDTO.getDishes().size());
        assertEquals("Dish 1", menuDTO.getDishes().get(0).getName());
        assertEquals(10.0, menuDTO.getDishes().get(0).getPrice(), 0.01);
        assertEquals("Dish 2", menuDTO.getDishes().get(1).getName());
        assertEquals(20.0, menuDTO.getDishes().get(1).getPrice(), 0.01);
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
