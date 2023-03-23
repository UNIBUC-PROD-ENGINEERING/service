package ro.unibuc.hello.dto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import ro.unibuc.hello.data.DishesEntity;


import java.util.Objects;
public class DishesDTO {
    @Id
    private String id;

    private String name;
    private int quantity;
    private float price;

    public DishesDTO(DishesEntity dish){
        id = dish.getId();
        name = dish.getName();
        quantity = dish.getQuantity();
        price = dish.getPrice();
    }
    public DishesDTO() {}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format(
                "Dish[name='%s', quantity(grams)='%d', price(EUR)='%f']",
                name, quantity, price);
    }
}
