package ro.unibuc.hello.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import ro.unibuc.hello.data.ClientEntity;
import ro.unibuc.hello.data.RestaurantEntity;
import ro.unibuc.hello.data.OrderEntity;
import ro.unibuc.hello.data.*;
import java.util.*;
import java.util.Arrays;

public class OrderDTO {
    private String id;

    @DBRef(lazy = true)
    private ClientDTO user;

    @DBRef(lazy = true)
    private RestaurantDTO restaurant;

    @DBRef(lazy = true)
    private List<DishesDTO> dishes;
    public OrderDTO() {}
    public OrderDTO(OrderEntity order) {
        id = order.getId();
        restaurant =  new RestaurantDTO(order.getRestaurant());
        user = new ClientDTO(order.getClient());
        List<DishesEntity> dishesEntitties = order.getDishes();
        List<DishesDTO> dishesDTOs = new ArrayList<DishesDTO>();
        for(int i = 0; i<dishesEntitties.size(); i++){
            DishesDTO  dishDTO = new DishesDTO(dishesEntitties.get(i));
            dishesDTOs.add(dishDTO);
        }
        dishes = dishesDTOs;

    }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public ClientDTO getUser() {
            return user;
        }

        public void setUser(ClientDTO user) {
            this.user = user;
        }



        public void setRestaurant(RestaurantDTO restaurant) {
            this.restaurant = restaurant;
        }
    @Override
    public String toString() {


        return String.format(
                "Order[user='%s', restaurant='%s', dishes:\n'%s']",
                user.getName(), restaurant.getName(), id);
    }
}



