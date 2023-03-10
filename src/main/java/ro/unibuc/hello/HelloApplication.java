package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = InformationRepository.class)
public class HelloApplication {

	@Autowired
	private InformationRepository informationRepository;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private DishesRepository dishesRepository;



	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		informationRepository.deleteAll();
		informationRepository.save(new InformationEntity("Overview",
				"This is an example of using a data storage engine running separately from our applications server"));

		clientRepository.deleteAll();
		menuRepository.deleteAll();
		orderRepository.deleteAll();
		restaurantRepository.deleteAll();
		dishesRepository.deleteAll();

		ClientEntity client1 = new ClientEntity("Cosmin", "cosmin@email.com", "Sector 4 Bucuresti");
		ClientEntity client2 = new ClientEntity("Florin", "florin@email.com", "Sector 3 Bucuresti");
		ClientEntity client3 = new ClientEntity("Daniel", "daniel@email.com", "Sector 1 Bucuresti");

		client1 = clientRepository.save(new ClientEntity(client1.getName(), client1.getEmail(), client1.getAddress()));
		client2 = clientRepository.save(new ClientEntity(client2.getName(), client2.getEmail(), client2.getAddress()));
		client3 = clientRepository.save(new ClientEntity(client3.getName(), client3.getEmail(), client3.getAddress()));

		DishesEntity dish1 = new DishesEntity("cartofi prajiti", 200, 3.50f);
		DishesEntity dish2 = new DishesEntity("supa de legume", 200, 5.50f);
		DishesEntity dish3 = new DishesEntity("snitel de porc", 200, 6.50f);
		DishesEntity dish4 = new DishesEntity("cotlet de miel", 200, 9.50f);
		DishesEntity dish5 = new DishesEntity("pizza carnivora", 500, 9.50f);

		dish1 = dishesRepository.save(new DishesEntity(dish1.getName(), dish1.getQuantity(), dish1.getPrice()));
		dish2 = dishesRepository.save(new DishesEntity(dish2.getName(), dish2.getQuantity(), dish2.getPrice()));
		dish3 = dishesRepository.save(new DishesEntity(dish3.getName(), dish3.getQuantity(), dish3.getPrice()));
		dish4 = dishesRepository.save(new DishesEntity(dish4.getName(), dish4.getQuantity(), dish4.getPrice()));
		dish5 = dishesRepository.save(new DishesEntity(dish5.getName(), dish5.getQuantity(), dish5.getPrice()));

		RestaurantEntity restaurant1 = new RestaurantEntity("Maestro", "maestro@gmail.com", "Sector 4 Bucuresti", null);

		ArrayList<DishesEntity> dishesList1 = new ArrayList<>(Arrays.asList(dish1, dish2, dish3));
		OrderEntity order1_res1 = new OrderEntity(client1, restaurant1, dishesList1);

		ArrayList<DishesEntity> dishesList2 = new ArrayList<>(Arrays.asList(dish3, dish5));
		OrderEntity order2_res1 = new OrderEntity(client3, restaurant1, dishesList2);

		ArrayList<DishesEntity> dishesList3 = new ArrayList<>(Arrays.asList(dish1, dish5, dish3));
		OrderEntity order3_res1 = new OrderEntity(client2, restaurant1, dishesList3);

		ArrayList<OrderEntity> ordersForRestaurant1 = new ArrayList<>(Arrays.asList(order1_res1, order2_res1, order3_res1));
		restaurant1.setOrders(ordersForRestaurant1);

		order1_res1 = orderRepository.save(new OrderEntity(order1_res1.getClient(), order1_res1.getRestaurant(), order1_res1.getDishes()));
		order2_res1 = orderRepository.save(new OrderEntity(order2_res1.getClient(), order2_res1.getRestaurant(), order2_res1.getDishes()));
		order3_res1 = orderRepository.save(new OrderEntity(order3_res1.getClient(), order3_res1.getRestaurant(), order3_res1.getDishes()));

		restaurant1 = restaurantRepository.save(new RestaurantEntity(restaurant1.getName(), restaurant1.getEmail(), restaurant1.getAddress(), restaurant1.getOrders()));

		RestaurantEntity restaurant2 = new RestaurantEntity("Maestroooo", "maestrooooo@gmail.com", "Sector 2 Bucuresti", null);

		ArrayList<DishesEntity> dishesList4 = new ArrayList<>(Arrays.asList(dish4));
		OrderEntity order1_res2 = new OrderEntity(client1, restaurant2, dishesList4);

		ArrayList<DishesEntity> dishesList5 = new ArrayList<>(Arrays.asList(dish4, dish1));
		OrderEntity order2_res2 = new OrderEntity(client3, restaurant2, dishesList5);

		ArrayList<DishesEntity> dishesList6 = new ArrayList<>(Arrays.asList(dish1, dish2, dish5));
		OrderEntity order3_res2 = new OrderEntity(client2, restaurant2, dishesList6);

		ArrayList<OrderEntity> ordersForRestaurant2 = new ArrayList<>(Arrays.asList(order1_res2, order2_res2, order3_res2));
		restaurant2.setOrders(ordersForRestaurant2);

		order1_res2 = orderRepository.save(new OrderEntity(order1_res2.getClient(), order1_res2.getRestaurant(), order1_res2.getDishes()));
		order2_res2 = orderRepository.save(new OrderEntity(order2_res2.getClient(), order2_res2.getRestaurant(), order2_res2.getDishes()));
		order3_res2 = orderRepository.save(new OrderEntity(order3_res2.getClient(), order3_res2.getRestaurant(), order3_res2.getDishes()));

		restaurant2 = restaurantRepository.save(new RestaurantEntity(restaurant2.getName(), restaurant2.getEmail(), restaurant2.getAddress(), restaurant2.getOrders()));

		ArrayList<DishesEntity> menu1_dishes = new ArrayList<>(Arrays.asList(dish1, dish2, dish3, dish4, dish5));
		MenuEntity menu1 = new MenuEntity(restaurant1, menu1_dishes);

		menu1 = menuRepository.save(new MenuEntity(menu1.getRestaurant(), menu1.getDishes()));

		ArrayList<DishesEntity> menu2_dishes = new ArrayList<>(Arrays.asList(dish1, dish2, dish3, dish5));
		MenuEntity menu2 = new MenuEntity(restaurant2, menu2_dishes);

		menu2 = menuRepository.save(new MenuEntity(menu2.getRestaurant(), menu2.getDishes()));
	}

}
