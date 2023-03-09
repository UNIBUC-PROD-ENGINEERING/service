package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import java.util.ArrayList;

public class OrderEntity {

    @Id
    private String id;

    @DBRef(lazy = true)
    private UserEntity user;

    @DBRef(lazy = true)
    private RestaurantEntity restaurant;

    @DBRef(lazy = true)
    private ArrayList<DishesEntity> dishes;

    public OrderEntity(UserEntity user, RestaurantEntity restaurant, DishesEntity dishes) {
        this.user = user;
        this.restaurant = restaurant;
        this.dishes = dishes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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
        String[] orderItems = Arrays.stream(dishes)
                .map(dish -> dish.toString)
                .toArray();

        return String.format(
                "Order[user='%s', restaurant='%s', dishes:\n'%s']",
                user.getName, restaurant.getName(), String.join("\n", orderItems));
    }
}