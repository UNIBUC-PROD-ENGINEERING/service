package ro.unibuc.hello.dto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import ro.unibuc.hello.data.MenuEntity;
import ro.unibuc.hello.data.RestaurantEntity;
import ro.unibuc.hello.data.DishesEntity;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Objects;
import java.util.List;

public class MenuDTO {
    private String id;
    private RestaurantDTO restaurant;
    private List<DishesDTO> dishes;

    public MenuDTO() {}

    public MenuDTO(MenuEntity menu){
        id = menu.getId();

//        List<DishesEntity> dishesEntities = menu.getDishes();
//
//        List<DishesDTO> dishesDTOs = new ArrayList<DishesDTO>();
//        for(int i = 0; i < dishesEntities.size(); i++) {
//            DishesDTO dishDTO = new DishesDTO(dishesEntities.get(i));
//
//            dishesDTOs.add(dishDTO);
//        }
//
//        dishes = dishesDTOs;
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

    public List<DishesDTO> getDishes() { return dishes; }

    public void setDishes(List<DishesDTO> dishes) { this.dishes = dishes; }

    @Override
    public String toString() {

        return String.format(
                "Menu[restaurant='%s', dishes:\n'%s']",
                restaurant.getName(), id);
    }
}
