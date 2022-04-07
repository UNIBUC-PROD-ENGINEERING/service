package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.IngredientEntity;
import ro.unibuc.hello.data.IngredientRepository;
import ro.unibuc.hello.dto.AddIngredientDto;
import ro.unibuc.hello.dto.IngredientDto;
import ro.unibuc.hello.exception.BadRequestException;
import ro.unibuc.hello.exception.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/ingredient")
    @ResponseBody
    public IngredientDto getIngredient(@RequestParam(name="name") String name) {
        var entity = ingredientRepository.findByName(name);
        if(entity == null) {
            throw new NotFoundException();
        }
        return new IngredientDto(entity);
    }

    @GetMapping("/ingredients")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<IngredientDto> getAllIngredients() {
        var entities = ingredientRepository.findAll();
        if (entities.size() == 0) {
            throw new NotFoundException();
        }
        var returnedEntities = entities.stream();


        return returnedEntities.map(IngredientDto::new).collect(Collectors.toList());
    }

    @PostMapping("/ingredient/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addIngredient(@RequestBody AddIngredientDto model) {

        if (model.price < 0) {
            throw new BadRequestException(new HashMap<>() {{
                put("price", "negative");
            }});
        }

        IngredientEntity ingredient = new IngredientEntity(model.name, model.price, model.calories, model.protein, model.carb, model.fat);
        ingredientRepository.save(ingredient);
    }

}
