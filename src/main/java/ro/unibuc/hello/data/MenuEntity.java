package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuEntity {

    @Id
    private String id;

    @DBRef(lazy = true)
    private RestaurantEntity restaurant;

    @DBRef(lazy = true)
    private ArrayList<DishesEntity> dishes;

    public MenuEntity() {}

    public MenuEntity(RestaurantEntity restaurant, ArrayList<DishesEntity> dishes) {
        this.restaurant = restaurant;
        this.dishes = dishes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RestaurantEntity getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }

    public ArrayList<DishesEntity> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<DishesEntity> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        String[] menuItems = (String[]) Arrays.stream(this.dishes.toArray())
                .map(dish -> dish.toString())
                .toArray();

        return String.format(
                "Menu[restaurant='%s', dishes:\n'%s']",
                restaurant.getName(), String.join("\n", menuItems));
    }
}