package ro.unibuc.hello.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.ProductDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.ProductService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody ProductDTO productDTO) throws EntityNotFoundException {
        productService.addProduct(productDTO);
    }

    @GetMapping("/getProducts")
    public List<ProductDTO> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{productId}")
    public ProductDTO getProductById(@PathVariable String productId) throws EntityNotFoundException {
        return productService.getProductById(productId);
    }

    @GetMapping("/filterByNameContains")
    public List<ProductDTO> getProductsByNameContains(@RequestParam String name) {
        return productService.getProductsByNameContains(name);
    }

    @GetMapping("/filterByCategory")
    public List<ProductDTO> getProductsByCategory(@RequestParam String categoryName) {
        return productService.getProductsByCategory(categoryName);
    }

    @GetMapping("/filterByPriceBetween")
    public List<ProductDTO> getProductsByPriceBetween(@RequestParam Float lowerBoundPrice, @RequestParam Float upperBoundPrice) {
        return productService.getProductsByPriceBetween(lowerBoundPrice, upperBoundPrice);
    }
}
