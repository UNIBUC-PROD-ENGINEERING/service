package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.OrderEntity;
import ro.unibuc.hello.data.OrderRepository;
import ro.unibuc.hello.data.RobotRepository; 
import ro.unibuc.hello.dto.OrderDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.data.OrderStatus;
import ro.unibuc.hello.data.RobotEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RobotRepository robotRepository; 

    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> entities = orderRepository.findAll();
        return entities.stream()
                .map(entity -> new OrderDTO(
                        entity.getId(),
                        entity.getRobotId(), 
                        entity.getStatus(),
                        entity.getItemId(),
                        entity.getQuantity(),
                        entity.getLocation()
                ))
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(String id) throws EntityNotFoundException {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found"));
        return new OrderDTO(
                entity.getId(),
                entity.getRobotId(),
                entity.getStatus(),
                entity.getItemId(),
                entity.getQuantity(),
                entity.getLocation()
        );
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        System.out.println("Creating order with robotId: " + orderDTO.getRobotId());
        
        // Check if the robot exists
        if (!robotRepository.existsById(orderDTO.getRobotId())) {
            throw new IllegalArgumentException("Robot with ID " + orderDTO.getRobotId() + " does not exist.");
        }
        System.out.println("Robot exists, proceeding with order creation.");
        
        // Retrieve the robot to check if it already has an active order
        RobotEntity robot = robotRepository.findById(orderDTO.getRobotId()).orElseThrow(() -> new IllegalArgumentException("Robot does not exist"));
        
        // Check if the robot has an active order
        if (robot.getCurrentOrderId() != null) {
            throw new IllegalArgumentException("The robot already has an active order. Please wait for it to finish.");
        }

        // Validate the quantity
        if (orderDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        // Create and save the new order
        OrderEntity order = new OrderEntity(
                orderDTO.getRobotId(),
                OrderStatus.PENDING,
                orderDTO.getItemId(),
                orderDTO.getQuantity(),
                orderDTO.getLocation()
        );

        orderRepository.save(order);
        System.out.println("Order saved with ID: " + order.getId());

        // Update the robot's currentOrderId to this new order's ID
        robot.setCurrentOrderId(order.getId());
        robotRepository.save(robot);

        // Return the saved order as a DTO
        return new OrderDTO(
                order.getId(),
                order.getRobotId(),
                order.getStatus(),
                order.getItemId(),
                order.getQuantity(),
                order.getLocation()
        );
    }

    public OrderDTO updateOrderStatus(String id, String status) throws EntityNotFoundException {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found"));

        try {
            order.setStatus(OrderStatus.valueOf(status.toUpperCase())); 
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        if (order.getStatus() == OrderStatus.COMPLETED) {
            order.setCompletedAt(LocalDateTime.now());
        }

        orderRepository.save(order);
        return new OrderDTO(
                order.getId(),
                order.getRobotId(), 
                order.getStatus(),
                order.getItemId(),
                order.getQuantity(),
                order.getLocation()
        );
    }

    public void deleteOrder(String id) throws EntityNotFoundException {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found"));
        orderRepository.delete(order);
    }

    public int countCanceledOrders() {
        return (int) orderRepository.findAll()
                .stream()
                .filter(order -> OrderStatus.CANCELED.equals(order.getStatus())) 
                .count();
    }

    public boolean hasActiveOrder() {
        return orderRepository.findAll().stream()
                .anyMatch(order -> OrderStatus.IN_PROGRESS.equals(order.getStatus()));
    }

    public int countCompletedOrders() {
        return (int) orderRepository.findAll()
                .stream()
                .filter(order -> OrderStatus.COMPLETED.equals(order.getStatus()))
                .count();
    }
}