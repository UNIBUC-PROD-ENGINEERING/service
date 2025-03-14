package ro.unibuc.hello.controller;

import jakarta.validation.Valid;  
import org.springframework.http.ResponseEntity;  
import org.springframework.http.HttpStatus;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.web.bind.annotation.*;  
import ro.unibuc.hello.dto.OrderDTO;  
import ro.unibuc.hello.exception.EntityNotFoundException;  
import ro.unibuc.hello.service.OrderService;  
import ro.unibuc.hello.data.OrderStatus;  


import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable String id) throws EntityNotFoundException {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public OrderDTO createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        if (orderService.hasActiveOrderForRobot(orderDTO.getRobotId())) {
            throw new IllegalStateException("This robot already has an active order. Please wait for it to finish.");
        }        
        return orderService.createOrder(orderDTO);
    }

    @PutMapping("/{id}/status")
    public OrderDTO updateOrderStatus(@PathVariable String id, @RequestParam String status) throws EntityNotFoundException {
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            return orderService.updateOrderStatus(id, orderStatus.name());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: must be PENDING, IN_PROGRESS, COMPLETED, or CANCELED");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) throws EntityNotFoundException {
        orderService.deleteOrder(id);
    }

    @GetMapping("/stats")
    public ResponseEntity<String> getOrderStats() {
        int completedOrders = orderService.countCompletedOrders();
        int canceledOrders = orderService.countCanceledOrders();
        return ResponseEntity.ok(
            String.format("Completed Orders: %d, Canceled Orders: %d", completedOrders, canceledOrders)
        );
    }

    @ExceptionHandler({EntityNotFoundException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleConflict(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
