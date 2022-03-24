package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class IngredientEntity {

    @Id
    public String id;
    public String name;
    public Integer price;
    public Integer protein;
    public Integer carb;
    public Integer fat;

    public IngredientEntity() {}

    public IngredientEntity(String name, Integer price, Integer protein, Integer carb, Integer fat) {
        this.name = name;
        this.price = price;
        this.protein = protein;
        this.carb = carb;
        this.fat = fat;
    }

    @Override
    public String toString() {
        return "IngredientEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", protein=" + protein +
                ", carb=" + carb +
                ", fat=" + fat +
                '}';
    }
}
