package ro.unibuc.hello.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;
import ro.unibuc.hello.data.IngredientEntity;
import ro.unibuc.hello.data.IngredientRepository;
import ro.unibuc.hello.dto.AddIngredientDto;
import ro.unibuc.hello.exception.BadRequestException;
import ro.unibuc.hello.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class IngredientControllerTest {

    @Mock
    IngredientRepository mockRepository;

    @InjectMocks
    IngredientController ingredientController = new IngredientController();

    @Test
    void getIngredient_Returns() {

        when(mockRepository.findByName("Test")).thenReturn(new IngredientEntity("testescu", 1, 1, 1, 1, 2));

        var res = ingredientController.getIngredient("Test");

        Assertions.assertEquals("testescu", res.name);
        Assertions.assertEquals(1, res.price);
        Assertions.assertEquals(1, res.calories);
        Assertions.assertEquals(1, res.protein);
        Assertions.assertEquals(1, res.carb);
        Assertions.assertEquals(2, res.fat);
    }

    @Test
    void getIngredient_Throws() {

        when(mockRepository.findByName(anyString())).thenReturn(null);

        try {
            ingredientController.getIngredient("Test");
            Assertions.fail();
        }
        catch (Exception e){
            Assertions.assertEquals(NotFoundException.class, e.getClass());
            Assertions.assertEquals("Not Found", e.getMessage());
        }
    }

    @Test
    public void getAllIngredients_Throws() {
        when(mockRepository.findAll()).thenReturn(List.of());

        try {
            var res = ingredientController.getAllIngredients();
            Assertions.fail();
        }
        catch (Exception e){
            Assertions.assertEquals(NotFoundException.class, e.getClass());
            Assertions.assertEquals("Not Found", e.getMessage());
        }
    }
    @Test
    void getAllIngredients_Returns() {
        when(mockRepository.findAll()).thenReturn(Arrays.asList(new IngredientEntity("a", 1, 1, 1, 1, 1),
                new IngredientEntity("b", 2, 2, 2, 2, 2),
                new IngredientEntity("c", 3, 2, 3, 3, 3)));

        var res = ingredientController.getAllIngredients();

        Assertions.assertEquals(3, res.size());
    }

    @Test
    void addIngredient() {
        var Ingredient = new AddIngredientDto("name", 1, 1, 1, 1, 1);
        ingredientController.addIngredient(Ingredient);

        verify(mockRepository, times(1)).save(any());
    }

    @Test
    void addIngredient_ThrowsBadRequest() {
        var Ingredient = new AddIngredientDto("name", -500, 1, 1, 1, 1);

        try {
            ingredientController.addIngredient(Ingredient);
            Assertions.fail();
        }
        catch (Exception e){
            Assertions.assertEquals(BadRequestException.class, e.getClass());
            Assertions.assertEquals("Bad Request", e.getMessage());
        }
    }
}