package ro.unibuc.hello.dto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import ro.unibuc.hello.data.MenuEntity;
import ro.unibuc.hello.data.RestaurantEntity;


import java.util.Arrays;
import java.util.Objects;
public class MenuDTO {
    @Id
    private String id;
    private RestaurantDTO restaurant;

    public MenuDTO(MenuEntity menu){
        id = menu.getId();
        RestaurantEntity restaurantDTO = menu.getRestaurant();
        if(restaurant != null){
            restaurant =new RestaurantDTO(restaurantDTO);
        }
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {


        return String.format(
                "Menu[restaurant='%s', dishes:\n'%s']",
                restaurant.getName(), id);
    }
}
