package ro.unibuc.tbd.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.tbd.model.Client;
import ro.unibuc.tbd.model.Meal;
import ro.unibuc.tbd.model.Order;
import ro.unibuc.tbd.repository.ClientRepository;
import ro.unibuc.tbd.repository.MealRepository;
import ro.unibuc.tbd.repository.OrderRepository;
import ro.unibuc.tbd.repository.RestaurantRepository;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ClientRepository clientRepository;
    private final MealRepository mealRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    OrderService(OrderRepository orderRepository,
            ClientRepository clientRepository,
            MealRepository mealRepository,
            RestaurantRepository restaurantRepository) {
        this.repository = orderRepository;
        this.clientRepository = clientRepository;
        this.mealRepository = mealRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Order getOrderById(String orderId) {
        Optional<Order> order = repository.findById(orderId);
        if (order.isPresent()) {
            return order.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found.");
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order createOrder(Order order) {
        Optional<Client> optionalClient = clientRepository.findById(order.clientId);
        if (optionalClient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found.");
        }

        if (order.meals.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "You can not place an order without any meals.");
        }

        order.totalPrice = 0.0f;
        for (Map.Entry<String, Integer> item : order.meals.entrySet()) {
            String mealId = item.getKey();
            Integer quantity = item.getValue();

            Optional<Meal> optionalMeal = mealRepository.findById(mealId);
            if (optionalMeal.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        item.getKey() + " is not a valid meal.");
            }

            order.totalPrice += optionalMeal.get().price * quantity;
        }

        return repository.save(order);
    }

    public Order updateOrder(String orderId, Order request) {
        Optional<Order> optionalOrder = repository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found.");
        }

        Order order = optionalOrder.get();
        order.updateOrder(request);

        return repository.save(order);
    }

    public Order deleteOrderById(String orderId) {
        Optional<Order> optionalOrder = repository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found.");
        }

        Order order = optionalOrder.get();

        repository.deleteById(orderId);
        return order;
    }
}
