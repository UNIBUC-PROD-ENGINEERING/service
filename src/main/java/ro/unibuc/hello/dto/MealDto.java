package ro.unibuc.hello.dto;

import java.util.List;

public class MealDto {

    public String id;
    public String name;
    public List<String> ingredients;
    public Integer portionSize;
    public Float price;

    public MealDto(String id, String name, List<String> ingredients, Integer portionSize, Float price) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.portionSize = portionSize;
        this.price = price;
    }
}
