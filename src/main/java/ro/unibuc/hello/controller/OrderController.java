package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.OrderDTO;
import ro.unibuc.hello.service.OrderService;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.ProductOrderEntity;

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
    public OrderDTO getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }
    
    @GetMapping("/history/{userId}")
    public List<OrderDTO> getOrderHistory(@PathVariable String userId) {
        return orderService.getOrderHistoryByUserId(userId);
    }
    
    @PostMapping
    public OrderDTO createOrder(@RequestBody UserEntity user, @RequestBody List<ProductOrderEntity> productOrders, @RequestParam String status) {
        return orderService.createOrder(user, productOrders, status);
    }
    
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
    }
    
    @PutMapping("/{id}/status")
    public OrderDTO updateOrderStatus(@PathVariable String id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }
}
