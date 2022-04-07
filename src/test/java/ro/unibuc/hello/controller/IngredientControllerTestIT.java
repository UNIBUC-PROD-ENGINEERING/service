package ro.unibuc.hello.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import ro.unibuc.hello.data.IngredientEntity;
import ro.unibuc.hello.data.IngredientRepository;
import ro.unibuc.hello.dto.AddIngredientDto;
import ro.unibuc.hello.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class IngredientControllerTestIT {

    @Autowired
    IngredientController ingredientController;

    @Autowired
    IngredientRepository ingredientRepository;

    @BeforeEach
    public void setUp() {
        var x = ingredientRepository.findByName("Nu Este");
        if(x != null)
            ingredientRepository.delete(x);

        var y = ingredientRepository.findByName("Este");
        if(y != null)
            ingredientRepository.delete(y);

        ingredientRepository.save(new IngredientEntity("Este", 2, 2, 2, 2, 2));
    }

    @Test
    @Order(1)
    public void getIngredient_Throws() {
        try {
            ingredientController.getIngredient("Nu Este");
            Assertions.fail();
        }
        catch (Exception e){
            Assertions.assertEquals(NotFoundException.class, e.getClass());
            Assertions.assertEquals("Not Found", e.getMessage());
        }
    }

    @Test
    @Order(2)
    void addIngredient() {
        var ingredient = new AddIngredientDto("Nu Este", 5, 5, 5, 5, 5);

        ingredientController.addIngredient(ingredient);

        Assertions.assertNotNull(ingredientRepository.findByName("Nu Este"));
    }

    @AfterAll
    public void clean() {
        var x = ingredientRepository.findByName("Nu Este");
        if(x != null)
            ingredientRepository.delete(x);

        var y = ingredientRepository.findByName("Este");
        if(y != null)
            ingredientRepository.delete(y);

      }
}