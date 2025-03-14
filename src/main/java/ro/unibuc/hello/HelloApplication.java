package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.*;

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
                "1",   // currentOrderId 
                5,     // completedOrders
                null            // errors
        );
        robot1.setId("robot001");
        robotRepository.save(robot1);

        RobotEntity robot2 = new RobotEntity(
                "IDLE",         // status
                null,   // no current order initially
                0,     // completedOrders
                null            // errors
        );
        robot2.setId("robot002");
        robotRepository.save(robot2);

        RobotEntity robot3 = new RobotEntity(
            "IDLE",         // status
            null,   // no current order initially
            0,     // completedOrders
            null            // errors
        );
        robot2.setId("robot003");
        robotRepository.save(robot3);

        // Initialize Orders
        OrderEntity order1 = new OrderEntity(
                "robot001",        // robotId
                OrderStatus.PENDING,       // status (PENDING for a valid scenario)
                "item123",          // itemId
                5,                // quantity
                "Aisle 1"         // location
        );
        order1.setId("1"); // Set order ID to 1
        orderRepository.save(order1);

        // Initialize Inventory
        InventoryEntity inventoryItem1 = new InventoryEntity(
            "item123",        // itemId
            "Item 1",         // itemName
            20,               // stock
            10                // reorder level
        );
        inventoryRepository.save(inventoryItem1);

        InventoryEntity inventoryItem2 = new InventoryEntity(
            "item124",        // itemId
            "Item 2",         // itemName
            50,               // stock
            10                // reorder level
        );
        inventoryRepository.save(inventoryItem2);

        InventoryEntity inventoryItem3 = new InventoryEntity(
            "item125",        // itemId
            "Item 3",         // itemName
            100,              // stock
            20                // reorder level
        );
        inventoryRepository.save(inventoryItem3);

        System.out.println("Database initialized with test data for orders, robots, and inventory.");
    }
}
