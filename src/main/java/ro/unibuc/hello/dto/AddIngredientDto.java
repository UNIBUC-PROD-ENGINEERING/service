package ro.unibuc.hello.dto;

public class AddIngredientDto {
    public String name;
    public Integer price;
    public Integer protein;
    public Integer carb;
    public Integer fat;
    public AddIngredientDto(String name, Integer price, Integer protein, Integer carb, Integer fat) {
        this.name = name;
        this.price = price;
        this.protein = protein;
        this.carb = carb;
        this.fat = fat;
    }
}
