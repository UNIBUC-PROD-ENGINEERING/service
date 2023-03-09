package ro.unibuc.hello.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import ro.unibuc.hello.data.ClientEntity;
import ro.unibuc.hello.data.RestaurantEntity;
import ro.unibuc.hello.data.OrderEntity;

import java.util.Arrays;

public class OrderDTO {
    @Id
    private String id;

    @DBRef(lazy = true)
    private ClientDTO user;

    @DBRef(lazy = true)
    private RestaurantDTO restaurant;

    public OrderDTO(OrderEntity order) {
        id = order.getId();
        RestaurantDTO restaurantDTO = new RestaurantDTO(order.getRestaurant());
        ClientDTO clientDTO = new ClientDTO(order.getClient());
        if (restaurant != null) {
            restaurant = restaurantDTO;
        }
        if (user != null) {
            user = clientDTO;
        }
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



