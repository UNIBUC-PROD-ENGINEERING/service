package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class IngredientEntity {

    @Id
    public String id;
    public String name;
    public int price;
    public int protein;
    public int carb;
    public int fat;


    public IngredientEntity(String name, int price, int protein, int carb, int fat) {
        this.name = name;
        this.price = price;
        this.protein = protein;
        this.carb = carb;
        this.fat = fat;
    }

}
