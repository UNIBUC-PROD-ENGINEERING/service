package ro.unibuc.tbd.model;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Meal {

    @Id
    public String id;

    public String restaurantId;
    public String name;
    public List<String> ingredients;
    public Integer portionSize;
    public Float price;

    public Meal updateMeal(Meal other) {
        if (other.getRestaurantId() != null) {
            this.restaurantId = other.restaurantId;
        }

        if (other.getName() != null) {
            this.name = other.name;
        }

        if (other.getIngredients() != null) {
            this.ingredients = other.ingredients;
        }

        if (other.getPortionSize() != null) {
            this.portionSize = other.portionSize;
        }

        if (other.getPrice() != null) {
            this.price = other.price;
        }

        return this;
    }
}
