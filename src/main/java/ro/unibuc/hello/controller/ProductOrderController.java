package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.ProductOrderDTO;
import ro.unibuc.hello.service.ProductOrderService;

import java.util.List;

@RestController
@RequestMapping("/product-orders")
public class ProductOrderController {
    
    @Autowired
    private ProductOrderService productOrderService;
    
    @GetMapping
    public List<ProductOrderDTO> getAllProductOrders() {
        return productOrderService.getAllProductOrders();
    }
    
    @PostMapping
    public ProductOrderDTO createProductOrder(@RequestBody ProductOrderDTO productOrderDTO) {
        return productOrderService.createProductOrder(productOrderDTO);
    }
}