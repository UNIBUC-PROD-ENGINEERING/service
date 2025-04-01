package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.CreateOrderRequest;
import ro.unibuc.hello.dto.OrderDTO;
import ro.unibuc.hello.service.OrderService;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.ProductEntity;
import ro.unibuc.hello.data.ProductOrderEntity;
import ro.unibuc.hello.data.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

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
    public OrderDTO createOrder(@RequestBody CreateOrderRequest request) {
        UserEntity user = new UserEntity();
        user.setId(request.getUserId());

        List<ProductOrderEntity> productOrders = request.getProductOrders().stream().map(dto -> {
            ProductEntity product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + dto.getProductId()));
            return new ProductOrderEntity(null, product, dto.getQuantity(), dto.getOrderedAt());
        }).collect(Collectors.toList());

        return orderService.createOrder(user, productOrders, request.getStatus());
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
