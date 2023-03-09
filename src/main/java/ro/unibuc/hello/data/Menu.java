package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuEntity {

    @Id
    private String id;

    @DBRef(lazy = true)
    private RestaurantEntity restaurant;

    @DBRef(lazy = true)
    private ArrayList<DishEntity> dishes;

    public MenuEntity(RestaurantEntity restaurant, ArrayList<DishEntity> dishes) {
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

    public ArrayList<DishEntity> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<DishEntity> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        String[] menuItems = Arrays.stream(dishes)
                                   .map(dish -> dish.toString)
                                   .toArray();

        return String.format(
                "Menu[restaurant='%s', dishes:\n'%s']",
                restaurant.getName(), String.join("\n", menuItems));
    }
}