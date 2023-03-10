package ro.unibuc.hello.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.RestaurantDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<RestaurantDTO> getRestaurants() {
        ArrayList<RestaurantDTO> restaurantDTOs = new ArrayList<>();

        restaurantRepository.findAll().forEach(restaurantEntity -> restaurantDTOs.add(new RestaurantDTO(restaurantEntity)));
        return restaurantDTOs;
    }

    public RestaurantDTO getRestaurant(String id) {
        RestaurantEntity restaurant = restaurantRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);

        if(restaurant != null)
            return new RestaurantDTO(restaurant);
        else
            return null;
    }

    public RestaurantDTO insertRestaurant(String name,
                                          String email,
                                          String address,
                                          List<String> orderIds)
    {
        RestaurantEntity restaurant = new RestaurantEntity(name, email, address);
        ArrayList<OrderEntity> orderEntities = new ArrayList<>();

        if(!orderIds.isEmpty() && orderIds != null)
            orderIds.forEach(id -> orderEntities.add(orderRepository.findById(
                    String.valueOf(new ObjectId(id))).orElse(null)));
        else
            restaurant.setOrders(null);

        if(!orderEntities.isEmpty() && orderEntities != null)
            restaurant.setOrders(orderEntities);

        return new RestaurantDTO(restaurantRepository.save(restaurant));
    }

    public RestaurantDTO updateRestaurant(String id,
                                          String name,
                                          String email,
                                          String address,
                                          List<String> orderIds)
    {
        RestaurantEntity restaurant =
                restaurantRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);

        if(restaurant != null)
        {
            if(name != null)
                restaurant.setName(name);
            if(email != null)
                restaurant.setEmail(email);
            if(address != null)
                restaurant.setAddress(address);
            if(!orderIds.isEmpty() && orderIds != null)
            {
                ArrayList<OrderEntity> orderEntities = new ArrayList<>();
                orderIds.forEach(orderId -> orderEntities.add(orderRepository.findById(
                        String.valueOf(new ObjectId(orderId))).orElse(null)));
                if(!orderEntities.isEmpty())
                    restaurant.setOrders(orderEntities);
            }
            else
                restaurant.setOrders(null);

            return new RestaurantDTO(restaurantRepository.save(restaurant));
        }
        else
            return null;
    }

    public String deleteRestaurant(String id){
        restaurantRepository.deleteById(String.valueOf(new ObjectId(id)));

        return "Restaurant with id " + id + " was deleted";
    }

}
