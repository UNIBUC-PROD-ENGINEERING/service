package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.IngredientEntity;

public class IngredientDto {

    public String id;
    public String name;
    public int price;
    public int calories;
    public int protein;
    public int carb;
    public int fat;

    public IngredientDto(IngredientEntity entity) {
        this.id = entity.id;
        this.name = entity.name;
        this.price = entity.price;
        this.calories = entity.calories;
        this.protein = entity.protein;
        this.carb = entity.carb;
        this.fat = entity.fat;
    }
}
