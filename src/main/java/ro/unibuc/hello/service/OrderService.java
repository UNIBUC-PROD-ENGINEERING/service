package ro.unibuc.hello.service;
import org.bson.types.ObjectId;
import  org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import  ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.ClientDTO;
import ro.unibuc.hello.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DishesRepository dishesRepository;

    public List<OrderDTO> getOrders() {
        return orderRepository
                .findAll()
                .stream()
                .map(order -> new OrderDTO(order))
                .collect(Collectors.toList());
    }

    public OrderDTO getOrder(String id) {
        OrderEntity order = orderRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);
        if (order != null) {
            return new OrderDTO(order);
        } else {
            return null;
        }
    }

    public OrderDTO insertOrder(String restaurantId, String clientId, List<String> dishesId) {
        ArrayList<OrderEntity> orderEntities = new ArrayList<>();
        ArrayList<DishesEntity> dishesEntities = new ArrayList<>();
        ClientEntity client = clientRepository.findById(String.valueOf(new ObjectId(clientId))).orElse(null);
        RestaurantEntity restaurant = restaurantRepository.findById(String.valueOf(new ObjectId(restaurantId))).orElse(null);
        OrderEntity order = new OrderEntity(client, restaurant, dishesEntities);


        if (!dishesId.isEmpty() && dishesId != null) {
            dishesId.forEach(id -> dishesEntities.add(dishesRepository.findById(
                    String.valueOf(new ObjectId(id))).orElse(null)));
        } else {
            order.setDishes(null);
        }

        if(!dishesEntities.isEmpty() && dishesEntities != null)
            order.setDishes(dishesEntities);


        return new OrderDTO(orderRepository.save(order));
    }

    public OrderDTO updateOrder(String orderId, String restaurantId, String clientId, List<String> dishesId) {
        OrderEntity order = orderRepository.findById(String.valueOf(new ObjectId(orderId))).orElse(null);
        if (order != null) {
            ClientEntity client = clientRepository.findById(String.valueOf(new ObjectId(clientId))).orElse(null);
            order.setUser(client);
            RestaurantEntity restaurant = restaurantRepository.findById(String.valueOf(new ObjectId(restaurantId))).orElse(null);
            order.setRestaurant(restaurant);
            ArrayList<DishesEntity> dishesEntities = new ArrayList<>();
            if (!dishesId.isEmpty() && dishesId != null) {
                dishesId.forEach(id -> dishesEntities.add(dishesRepository.findById(String.valueOf(new ObjectId(id))).orElse(null)));
            } else {
                order.setDishes(null);
            }

            if(!dishesEntities.isEmpty() && dishesEntities != null)
                order.setDishes(dishesEntities);

            return new OrderDTO(orderRepository.save(order));
        }
        else
            return null;
    }

    public  String deleteOrder(String id){
    orderRepository.deleteById(String.valueOf(new ObjectId(id)));
    return "Order with id:" + id + "was deleted";
    }
}



