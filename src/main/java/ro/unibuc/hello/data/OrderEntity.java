package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.data.mongodb.core.mapping.DBRef;


public class OrderEntity {

    @Id
    private String id;

    @DBRef(lazy = true)
    private ClientEntity user;

    @DBRef(lazy = true)
    private RestaurantEntity restaurant;

    @DBRef(lazy = true)
    private ArrayList<DishesEntity> dishes;

    public OrderEntity(ClientEntity user, RestaurantEntity restaurant, ArrayList<DishesEntity> dishes) {
        this.user = user;
        this.restaurant = restaurant;
        this.dishes = dishes;
    }

    public OrderEntity(String id,ClientEntity user, RestaurantEntity restaurant, ArrayList<DishesEntity> dishes) {
        this.id = id;
        this.user = user;
        this.restaurant = restaurant;
        this.dishes = dishes;
    }


    public OrderEntity() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClientEntity getUser() {
        return user;
    }

    public void setUser(ClientEntity user) {
        this.user = user;
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

    public RestaurantEntity getRestaurant() {
        return this.restaurant;
    }
    public ClientEntity getClient(){
        return this.user;
    }



    @Override
    public String toString() {
        String[] orderItems = (String[]) Arrays.stream(this.dishes.toArray())
                .map(dish -> dish.toString())
                .toArray();

        return String.format(
                "Order[user='%s', restaurant='%s', dishes:\n'%s']",
                user.getName(), restaurant.getName(), String.join("\n", orderItems));
    }
}