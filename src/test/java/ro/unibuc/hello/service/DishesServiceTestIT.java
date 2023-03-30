package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.*;
import ro.unibuc.hello.dto.*;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Tag("IT")

public class DishesServiceTestIT {

    @Autowired
    DishesRepository dishesRepository;

    @Autowired
    DishesService dishesService;

    @Test
    public void testGetDishes(){
        dishesRepository.deleteAll();
        List<DishesDTO> dishes = dishesService.getDishes();
        Assertions.assertNotNull(dishes);
        Assertions.assertEquals(0, dishes.size());

    }

    @Test
    public void testInsertDishes(){
        DishesDTO dishesDTO = dishesService.insertDish("Wok de pui", 1, 35);

        DishesDTO dishesInsertedDTO = dishesService.getDish(dishesDTO.getId());

        Assertions.assertNotNull(dishesDTO);
        Assertions.assertNotNull(dishesInsertedDTO);

    }

    @Test
    public void testUpdateDishes(){
        DishesDTO dishesDTO = dishesService.insertDish("Wok de pui", 1, 35);

        DishesDTO updatedDishesDTO = dishesService.updateDish(dishesDTO.getId(), "Friptura de vita cu piure de cartofi", 4, 56);

        DishesDTO dishesUpdatedDTO = dishesService.getDish(updatedDishesDTO.getId());

        Assertions.assertNotNull(updatedDishesDTO);
        Assertions.assertNotNull(dishesUpdatedDTO);


    }

    @Test
    public void testDeleteDishes(){
        dishesRepository.deleteAll();

        DishesDTO dishesDTO = dishesService.insertDish("Supa crema de cartofi", 5, 23);

        String deleteMessage = dishesService.deleteDish(dishesDTO.getId().toString());

        Assertions.assertEquals("Dish with id " + dishesDTO.getId().toString() + " was deleted successfully!", deleteMessage);

        Assertions.assertNull(dishesService.getDish(dishesDTO.getId()));

    }


}
