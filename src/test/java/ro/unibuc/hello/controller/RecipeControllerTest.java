package ro.unibuc.hello.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.RecipeEntity;
import ro.unibuc.hello.data.RecipeEntity;
import ro.unibuc.hello.data.RecipeRepository;
import ro.unibuc.hello.dto.AddRecipeDto;
import ro.unibuc.hello.exception.BadRequestException;
import ro.unibuc.hello.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class RecipeControllerTest {

    @Mock
    RecipeRepository mockRepository;

    @InjectMocks
    RecipeController recipeController = new RecipeController();

    @Test
    void getRecipe_Returns() {

        ArrayList<String> mockRecipes = new ArrayList<String>(Arrays.asList("a", "b", "c"));
        when(mockRepository.findByName("Test")).thenReturn(new RecipeEntity("testescu", mockRecipes));

        var res = recipeController.getRecipe("Test");

        Assertions.assertEquals("testescu", res.name);
        Assertions.assertEquals(3, res.ingredientsNames.size());
    }

    @Test
    void getRecipe_Throws() {

        when(mockRepository.findByName(anyString())).thenReturn(null);

        try {
            recipeController.getRecipe("Test");
            Assertions.fail();
        }
        catch (Exception e){
            Assertions.assertEquals(NotFoundException.class, e.getClass());
            Assertions.assertEquals("Not Found", e.getMessage());
        }
    }

    @Test
    public void getAllRecipes_Throws() {
        when(mockRepository.findAll()).thenReturn(List.of());

        try {
            var res = recipeController.getAllRecipes();
            Assertions.fail();
        }
        catch (Exception e){
            Assertions.assertEquals(NotFoundException.class, e.getClass());
            Assertions.assertEquals("Not Found", e.getMessage());
        }
    }
    @Test
    void getAllRecipes_Returns() {
        ArrayList<String> mockRecipes1 = new ArrayList<String>(Arrays.asList("a", "b", "c"));
        ArrayList<String> mockRecipes2 = new ArrayList<String>(Arrays.asList("d", "e", "d"));
        ArrayList<String> mockRecipes3 = new ArrayList<String>(Arrays.asList("g", "h", "i"));
        when(mockRepository.findAll()).thenReturn(Arrays.asList(new RecipeEntity("a", mockRecipes1),
                new RecipeEntity("b", mockRecipes2),
                new RecipeEntity("c", mockRecipes3)));

        var res = recipeController.getAllRecipes();

        Assertions.assertEquals(3, res.size());
    }

    @Test
    void addRecipe() {
        ArrayList<String> mockRecipe = new ArrayList<String>(Arrays.asList("g", "h", "i"));
        var Recipe = new AddRecipeDto("name", mockRecipe);
        recipeController.addRecipe(Recipe);

        verify(mockRepository, times(1)).save(any());
    }

    @Test
    void addRecipe_ThrowsBadRequest() {
        ArrayList<String> mockRecipe = new ArrayList<String>();
        var Recipe = new AddRecipeDto("name", mockRecipe);

        try {
            recipeController.addRecipe(Recipe);
            Assertions.fail();
        }
        catch (Exception e){
            Assertions.assertEquals(NotFoundException.class, e.getClass());
            Assertions.assertEquals("Not Found", e.getMessage());
        }
    }


}