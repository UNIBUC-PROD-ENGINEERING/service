package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.OrderEntity;
import ro.unibuc.hello.data.OrderRepository;
import ro.unibuc.hello.data.OrderStatus;
import ro.unibuc.hello.data.RobotEntity;
import ro.unibuc.hello.data.RobotRepository;
import ro.unibuc.hello.data.InventoryEntity;
import ro.unibuc.hello.data.InventoryRepository;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {OrderRepository.class, RobotRepository.class, InventoryRepository.class})
public class HelloApplication {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }

    @PostConstruct
    public void runAfterObjectCreated() {
        // Clear existing data
        orderRepository.deleteAll();
        robotRepository.deleteAll();
        inventoryRepository.deleteAll();

        // Initialize Robots
        RobotEntity robot1 = new RobotEntity(
                "IDLE",         // status
                null,   // currentOrderId
                0,     // completedOrders
                null            // errors
        );
        robot1.setId("robot001"); 
        robotRepository.save(robot1);

        // Initialize Orders
        OrderEntity testOrder = new OrderEntity(
                "robot001",        // robotId
                OrderStatus.IN_PROGRESS,   // status
                "item123",          // itemId
                5,                // quantity
                "Aisle 3"         // location
        );
        orderRepository.save(testOrder);

        // Initialize Inventory
        InventoryEntity inventoryItem = new InventoryEntity(
            "item1",
            "Item 1",
            10,
            3
        );
        inventoryRepository.save(inventoryItem);

        System.out.println("Database initialized with test data for orders, robots, and inventory.");
    }
}