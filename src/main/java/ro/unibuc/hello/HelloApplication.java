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
		RestaurantEntity restaurant2 = new RestaurantEntity("PizzaHut", "pizzahut@gmail.com", "Sector 1 Bucuresti", null);
		RestaurantEntity restaurant3 = new RestaurantEntity("Pizza Bonita", "bonita@gmail.com", "Sector 2 Bucuresti", null);
		RestaurantEntity restaurant4 = new RestaurantEntity("Dominos's", "domino@gmail.com", "Sector 1 Bucuresti", null);

		restaurant1 = restaurantRepository.save(new RestaurantEntity(restaurant1.getName(), restaurant1.getEmail(), restaurant1.getAddress(), restaurant1.getOrders()));
		restaurant2 = restaurantRepository.save(new RestaurantEntity(restaurant2.getName(), restaurant2.getEmail(), restaurant2.getAddress(), restaurant2.getOrders()));
		restaurant3 = restaurantRepository.save(new RestaurantEntity(restaurant3.getName(), restaurant3.getEmail(), restaurant3.getAddress(), restaurant3.getOrders()));
		restaurant4 = restaurantRepository.save(new RestaurantEntity(restaurant4.getName(), restaurant4.getEmail(), restaurant4.getAddress(), restaurant4.getOrders()));

		final DishesEntity finalDish1 = dish1;
		final DishesEntity finalDish2 = dish2;
		final DishesEntity finalDish3 = dish3;
		final DishesEntity finalDish4 = dish4;
		final DishesEntity finalDish5 = dish5;

		ArrayList<DishesEntity> dishesList1 = new ArrayList<>(){{
			add(finalDish1);
			add(finalDish2);
			add(finalDish3);
		}};

		ArrayList<DishesEntity> dishesList2 = new ArrayList<>(){{
			add(finalDish1);
			add(finalDish2);
			add(finalDish3);
			add(finalDish4);
		}};

		ArrayList<DishesEntity> dishesList3 = new ArrayList<>(){{
			add(finalDish1);
			add(finalDish2);
			add(finalDish4);
		}};

		ArrayList<DishesEntity> dishesList4 = new ArrayList<>(){{
			add(finalDish1);
			add(finalDish4);
			add(finalDish5);
		}};

		OrderEntity order1_res1 = new OrderEntity(client1, restaurant1, dishesList1);
		order1_res1 = orderRepository.save(new OrderEntity(order1_res1.getClient(), order1_res1.getRestaurant(), order1_res1.getDishes()));

		OrderEntity order2_res1 = new OrderEntity(client2, restaurant2, dishesList2);
		order2_res1 = orderRepository.save(new OrderEntity(order2_res1.getClient(), order2_res1.getRestaurant(), order2_res1.getDishes()));

		OrderEntity order3_res1 = new OrderEntity(client3, restaurant3, dishesList3);
		order3_res1 = orderRepository.save(new OrderEntity(order3_res1.getClient(), order3_res1.getRestaurant(), order3_res1.getDishes()));

		OrderEntity order4_res1 = new OrderEntity(client2, restaurant4, dishesList4);
		order4_res1 = orderRepository.save(new OrderEntity(order4_res1.getClient(), order4_res1.getRestaurant(), order4_res1.getDishes()));


		final OrderEntity finalOrder1 = order1_res1;
		final OrderEntity finalOrder2 = order2_res1;
		final OrderEntity finalOrder3 = order3_res1;
		final OrderEntity finalOrder4 = order4_res1;


		ArrayList<OrderEntity> ordersForRestaurant2 = new ArrayList<>(){{
			add(finalOrder1);
			add(finalOrder4);
			add(finalOrder3);
		}};

		ArrayList<OrderEntity> ordersForRestaurant1 = new ArrayList<>(){{
			add(finalOrder1);
			add(finalOrder4);
			add(finalOrder3);
			add(finalOrder2);
		}};

		ArrayList<OrderEntity> ordersForRestaurant3 = new ArrayList<>(){{
			add(finalOrder1);
			add(finalOrder4);
		}};

		ArrayList<OrderEntity> ordersForRestaurant4 = new ArrayList<>(){{
			add(finalOrder1);
			add(finalOrder2);
			add(finalOrder3);
		}};


		restaurant2.setOrders(ordersForRestaurant2);
		restaurant3.setOrders(ordersForRestaurant3);
		restaurant1.setOrders(ordersForRestaurant1);
		restaurant4.setOrders(ordersForRestaurant4);


		restaurant2 = restaurantRepository.save(new RestaurantEntity(restaurant2.getName(), restaurant2.getEmail(), restaurant2.getAddress(), restaurant2.getOrders()));
		restaurant3 = restaurantRepository.save(new RestaurantEntity(restaurant3.getName(), restaurant3.getEmail(), restaurant3.getAddress(), restaurant3.getOrders()));
		restaurant4 = restaurantRepository.save(new RestaurantEntity(restaurant4.getName(), restaurant4.getEmail(), restaurant4.getAddress(), restaurant4.getOrders()));
		restaurant1 = restaurantRepository.save(new RestaurantEntity(restaurant1.getName(), restaurant1.getEmail(), restaurant1.getAddress(), restaurant1.getOrders()));

		ArrayList<DishesEntity> menu1_dishes = new ArrayList<>(Arrays.asList(dish1, dish2, dish3, dish4, dish5));
		MenuEntity menu1 = new MenuEntity(restaurant1, menu1_dishes);

		menu1 = menuRepository.save(new MenuEntity(menu1.getRestaurant(), menu1.getDishes()));

		ArrayList<DishesEntity> menu2_dishes = new ArrayList<>(Arrays.asList(dish1, dish2, dish3, dish5));
		MenuEntity menu2 = new MenuEntity(restaurant2, menu2_dishes);

		menu2 = menuRepository.save(new MenuEntity(menu2.getRestaurant(), menu2.getDishes()));


	}

}
