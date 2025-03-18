package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.hello.data.ProductOrderEntity;
import ro.unibuc.hello.dto.OrderDTO;
import ro.unibuc.hello.service.OrderService;
import ro.unibuc.hello.data.UserEntity;


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
    
    @GetMapping("/history/{userId}")
    public List<OrderDTO> getOrderHistory(@PathVariable String userId) {
        return orderService.getOrderHistoryByUserId(userId);
    }
     @PostMapping
    public OrderDTO createOrder(@RequestBody UserEntity user, @RequestBody List<ProductOrderEntity> productOrders, @RequestParam String status) {
        return orderService.createOrder(user, productOrders, status);
    }
}