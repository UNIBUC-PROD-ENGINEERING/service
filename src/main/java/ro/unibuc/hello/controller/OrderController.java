package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.OrderDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.OrderService;

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
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PutMapping("/{id}/status")
    public OrderDTO updateOrderStatus(@PathVariable String id, @RequestParam String status) throws EntityNotFoundException {
        return orderService.updateOrderStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) throws EntityNotFoundException {
        orderService.deleteOrder(id);
    }
}
