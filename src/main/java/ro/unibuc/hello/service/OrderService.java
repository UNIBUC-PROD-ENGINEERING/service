package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.OrderEntity;
import ro.unibuc.hello.data.OrderRepository;
import ro.unibuc.hello.dto.OrderDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> entities = orderRepository.findAll();
        return entities.stream()
                .map(entity -> new OrderDTO(entity.getId(), entity.getWorkerId(), entity.getStatus(), entity.getItemId(), entity.getQuantity(), entity.getLocation()))
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(String id) throws EntityNotFoundException {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found"));
        return new OrderDTO(entity.getId(), entity.getWorkerId(), entity.getStatus(), entity.getItemId(), entity.getQuantity(), entity.getLocation());
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        OrderEntity order = new OrderEntity(orderDTO.getWorkerId(), "pending", orderDTO.getItemId(), orderDTO.getQuantity(), orderDTO.getLocation());
        orderRepository.save(order);
        return new OrderDTO(order.getId(), order.getWorkerId(), order.getStatus(), order.getItemId(), order.getQuantity(), order.getLocation());
    }

    public OrderDTO updateOrderStatus(String id, String status) throws EntityNotFoundException {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found"));
        order.setStatus(status);
        if (status.equals("completed")) {
            order.setCompletedAt(LocalDateTime.now());
        }
        orderRepository.save(order);
        return new OrderDTO(order.getId(), order.getWorkerId(), order.getStatus(), order.getItemId(), order.getQuantity(), order.getLocation());
    }

    public void deleteOrder(String id) throws EntityNotFoundException {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found"));
        orderRepository.delete(order);
    }
}
