package ro.unibuc.hello.dto;

public class AddIngredientDto {
    public String name;
    public int price;
    public int calories;
    public int protein;
    public int carb;
    public int fat;
    public AddIngredientDto(String name, int price, int calories, int protein, int carb, int fat) {
        this.name = name;
        this.price = price;
        this.calories = calories;
        this.protein = protein;
        this.carb = carb;
        this.fat = fat;
    }
}
