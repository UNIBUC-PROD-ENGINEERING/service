package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import java.util.ArrayList;

public class ClientEntity {

    @Id
    private String id;

    private String name;
    private int quantity;
    private float price;

    public DishesEntity(String name, int quantity, float price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

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