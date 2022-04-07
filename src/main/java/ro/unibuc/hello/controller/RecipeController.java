package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.IngredientEntity;
import ro.unibuc.hello.data.IngredientRepository;
import ro.unibuc.hello.data.RecipeEntity;
import ro.unibuc.hello.data.RecipeRepository;
import ro.unibuc.hello.dto.AddIngredientDto;
import ro.unibuc.hello.dto.AddRecipeDto;
import ro.unibuc.hello.dto.IngredientDto;
import ro.unibuc.hello.dto.RecipeDto;
import ro.unibuc.hello.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RecipeController {


    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/recipe")
    @ResponseBody
    public RecipeDto getRecipe(@RequestParam(name="name") String name) {
        var entity = recipeRepository.findByName(name);
        if(entity == null) {
            throw new NotFoundException();
        }

//        for (int i = 0; i < entity.ingredientsNames.size(); i++) {
//            System.out.println(entity.ingredientsNames.get(i));
//        }
        return new RecipeDto(entity);
    }

    @PostMapping("/recipe/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRecipe(@RequestBody AddRecipeDto model) {

        if (model.ingredientsNames.size() == 0) {
            throw new NotFoundException();
        }


        for (int i = 0; i < model.ingredientsNames.size(); i++) {
            var entity = ingredientRepository.findByName(model.ingredientsNames.get(i));
            if(entity == null) {
                throw new NotFoundException();
            }
//            if(entity != null)
            System.out.println(model.ingredientsNames.get(i));
        }
        RecipeEntity recipe = new RecipeEntity(model.name, model.ingredientsNames);
        recipeRepository.save(recipe);
    }

    @GetMapping("/recipes")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<RecipeDto> getAllRecipes() {
        var entities = recipeRepository.findAll();
        if (entities.size() == 0) {
            throw new NotFoundException();
        }
        var returnedEntities = entities.stream();


        return returnedEntities.map(RecipeDto::new).collect(Collectors.toList());
    }
}
