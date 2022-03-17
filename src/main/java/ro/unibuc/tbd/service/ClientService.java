package ro.unibuc.tbd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.tbd.model.CartRequestDTO;
import ro.unibuc.tbd.model.Client;
import ro.unibuc.tbd.model.Meal;
import ro.unibuc.tbd.model.Order;
import ro.unibuc.tbd.repository.ClientRepository;
import ro.unibuc.tbd.repository.MealRepository;


@Service
public class ClientService {

    private final ClientRepository repository;
    private final MealRepository mealRepository;
    private final OrderService orderService;

    @Autowired
    ClientService(ClientRepository clientRepository, MealRepository mealRepository,
            OrderService orderService) {
        this.repository = clientRepository;
        this.mealRepository = mealRepository;
        this.orderService = orderService;
    }

    public Client getClientById(String clientId) {
        Optional<Client> client = repository.findById(clientId);
        if (client.isPresent()) {
            return client.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found.");
    }

    public List<Client> getAllClients() {
        return repository.findAll();
    }

    public Client createClient(Client client) {
        client.cart = new HashMap<>();

        repository.save(client);
        return client;
    }

    public Client updateClient(String clientId, Client request) {
        Client client = this.getClientById(clientId);

        client.updateClient(request);

        repository.save(client);
        return client;
    }

    public Client deleteClientById(String clientId) {
        Client client = this.getClientById(clientId);

        repository.deleteById(clientId);
        return client;
    }

    public Client addToCart(String clientId, CartRequestDTO cartRequest) {
        Client client = this.getClientById(clientId);

        Optional<Meal> optionalMeal = mealRepository.findById(cartRequest.mealId);
        if (optionalMeal.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal not found.");
        }

        Meal meal = optionalMeal.get();

        client.addToCart(meal.id);

        repository.save(client);
        return client;
    }

    public Client removeFromCart(String clientId, CartRequestDTO cartRequest) {
        Client client = this.getClientById(clientId);

        client.removeFromCart(cartRequest.mealId);

        repository.save(client);
        return client;
    }

    public Client clearCart(String clientId) {
        Client client = this.getClientById(clientId);

        client.clearCart();

        repository.save(client);
        return client;
    }

    public Client placeOrder(String clientId) {
        Client client = this.getClientById(clientId);

        Order order = new Order();
        order.setClientId(client.id);
        order.setMeals(client.cart);

        orderService.createOrder(order);
        return this.clearCart(client.id);
    }
}
