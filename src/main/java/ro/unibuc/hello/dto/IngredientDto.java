package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.IngredientEntity;

public class IngredientDto {

    public String id;
    public String name;
    public Integer price;
    public Integer protein;
    public Integer carb;
    public Integer fat;

    public IngredientDto(IngredientEntity entity) {
        this.id = entity.id;
        this.name = entity.name;
        this.price = entity.price;
        this.protein = entity.protein;
        this.carb = entity.carb;
        this.fat = entity.fat;
    }
}
