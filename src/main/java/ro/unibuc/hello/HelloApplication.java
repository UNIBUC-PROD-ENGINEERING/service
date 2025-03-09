package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.OrderEntity;
import ro.unibuc.hello.data.OrderRepository;

import jakarta.annotation.PostConstruct;

import java.time.LocalDateTime;
import java.util.Collections;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = OrderRepository.class)
public class HelloApplication {

    @Autowired
    private OrderRepository orderRepository;

    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }

    @PostConstruct
	public void runAfterObjectCreated() {
		orderRepository.deleteAll();
	
		OrderEntity testOrder = new OrderEntity(
				"worker001",       // workerId
				"in_progress",     // status
				"item123",         // itemId
				5,                 // quantity
				"Aisle 3"          // location
		);

        orderRepository.save(testOrder);

        System.out.println("Database initialized with test orders.");
    }
	
}
