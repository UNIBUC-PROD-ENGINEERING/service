package main.java.ro.unibuc.hello.controller;

import main.java.ro.unibuc.hello.dto.ProductDTO;
import main.java.ro.unibuc.hello.exception.EntityNotFoundException;
import main.java.ro.unibuc.hello.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable String id) throws EntityNotFoundException {
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productService.saveProduct(productDTO);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) throws EntityNotFoundException {
        return productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) throws EntityNotFoundException {
        productService.deleteProduct(id);
    }
}
