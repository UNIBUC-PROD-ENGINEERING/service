package ro.unibuc.hello.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.unibuc.hello.exception.*;
import ro.unibuc.hello.exception.*;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.OrderDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<OrderDTO> getAllOrders() {
        logger.info("Fetching all orders...");
        List<OrderEntity> entities = orderRepository.findAll();
        logger.debug("Fetched {} orders", entities.size());

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

    public OrderDTO getOrderById(String id) {
        logger.info("Fetching order with ID: {}", id);
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Order with ID {} not found", id);
                    return new EntityNotFoundException("Order with ID " + id + " not found");
                });

        logger.debug("Found order: {}", entity);
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
        logger.info("Creating order with robotId: {}", orderDTO.getRobotId());

        // Check if the robot exists
        RobotEntity robot = robotRepository.findById(orderDTO.getRobotId())
                .orElseThrow(() -> {
                    logger.error("Robot with ID {} not found", orderDTO.getRobotId());
                    return new RobotNotFoundException(orderDTO.getRobotId());
                });

        // Check if the robot has an active order
        if (robot.getCurrentOrderId() != null) {
            logger.warn("Robot {} already has an active order", orderDTO.getRobotId());
            throw new RobotBusyException(orderDTO.getRobotId());
        }

        // Validate the quantity
        if (orderDTO.getQuantity() <= 0) {
            logger.warn("Invalid quantity: {}", orderDTO.getQuantity());
            throw new InvalidQuantityException(orderDTO.getQuantity());
        }

        // Check if the item exists in inventory
        InventoryEntity inventoryItem = inventoryRepository.findById(orderDTO.getItemId())
                .orElseThrow(() -> {
                    logger.error("Item with ID {} not found", orderDTO.getItemId());
                    return new ItemNotFoundException(orderDTO.getItemId());
                });

        // Check stock availability
        if (inventoryItem.getStock() < orderDTO.getQuantity()) {
            logger.warn("Insufficient stock for item {}: Available {}, Requested {}", orderDTO.getItemId(), inventoryItem.getStock(), orderDTO.getQuantity());
            throw new InsufficientStockException(orderDTO.getItemId(), inventoryItem.getStock(), orderDTO.getQuantity());
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
        logger.info("Order saved with ID: {}", order.getId());

        // Update the robot's currentOrderId to this new order's ID
        robot.setCurrentOrderId(order.getId());
        robotRepository.save(robot);
        logger.debug("Robot {} updated with new order ID {}", robot.getId(), order.getId());

        return new OrderDTO(
                order.getId(),
                order.getRobotId(),
                order.getStatus(),
                order.getItemId(),
                order.getQuantity(),
                order.getLocation()
        );
    }

    public OrderDTO updateOrderStatus(String id, String status) {
        logger.info("Updating order {} status to {}", id, status);

        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Order with ID {} not found for update", id);
                    return new EntityNotFoundException("Order with ID " + id + " not found");
                });

        try {
            order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid status: {}", status);
            throw new InvalidStatusException(status);
        }

        if (order.getStatus() == OrderStatus.COMPLETED) {
            order.setCompletedAt(LocalDateTime.now());
        }

        orderRepository.save(order);
        logger.info("Order {} updated to status {}", id, status);

        return new OrderDTO(
                order.getId(),
                order.getRobotId(),
                order.getStatus(),
                order.getItemId(),
                order.getQuantity(),
                order.getLocation()
        );
    }

    public void deleteOrder(String id) {
        logger.info("Deleting order with ID: {}", id);

        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Order with ID {} not found for deletion", id);
                    return new EntityNotFoundException("Order with ID " + id + " not found");
                });

        orderRepository.delete(order);
        logger.info("Order {} successfully deleted", id);
    }

    public boolean hasActiveOrderForRobot(String robotId) {
        logger.info("Checking if robot {} has an active order...", robotId);
        return orderRepository.findAll().stream()
                .anyMatch(order -> order.getRobotId().equals(robotId) &&
                        (order.getStatus().equals(OrderStatus.IN_PROGRESS) || order.getStatus().equals(OrderStatus.PENDING))
                );
    }


    public int countCompletedOrders() {
        logger.info("Counting completed orders...");
        return (int) orderRepository.findAll().stream()
                .filter(order -> order.getStatus().equals(OrderStatus.COMPLETED))
                .count();
    }

    public int countCanceledOrders() {
        logger.info("Counting canceled orders...");
        return (int) orderRepository.findAll().stream()
                .filter(order -> order.getStatus().equals(OrderStatus.CANCELED))
                .count();
    }
}
