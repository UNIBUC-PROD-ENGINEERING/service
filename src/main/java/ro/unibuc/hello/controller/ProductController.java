package ro.unibuc.hello.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.ProductDTO;
import ro.unibuc.hello.service.ProductService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/addProduct")
    void addProduct() {
        ProductDTO productDTO = ProductDTO.builder().build();
        productService.addProduct(productDTO);
    }

    @GetMapping("/getProducts")
    List<ProductDTO> getAllProducts() {

        return productService.getProducts();
    }
}
