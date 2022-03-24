package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.RecipeEntity;

import java.util.ArrayList;

public class RecipeDto {

    public String id;
    public String name;
    public ArrayList<String> ingredientsNames;

    public RecipeDto(RecipeEntity entity) {
        this.id = entity.id;
        this.name = entity.name;
        this.ingredientsNames = entity.ingredientsNames;
    }
}
