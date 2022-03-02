package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

import java.util.List;

public class MealEntity {

    @Id
    public String id;

    public String name;
    public List<String> ingredients;
    public Integer portionSize;
    public Float price;

    public MealEntity() {};

    public MealEntity(String id, String name, List<String> ingredients, Integer portionSize, Float price) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.portionSize = portionSize;
        this.price = price;
    }

    @Override
    public String toString() {
        return "MealEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", portionSize=" + portionSize +
                ", price=" + price +
                '}';
    }
}
