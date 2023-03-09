package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.hello.data.product.ProductDTO;
import ro.unibuc.hello.data.product.ProductEntity;
import ro.unibuc.hello.service.ProductService;

import java.util.List;


@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/api/products")
    @ResponseBody
    public void postProduct(@RequestBody ProductDTO postProduct){
        try {
            productService.insertProduct(postProduct);
        }
        catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.BAD_REQUEST.toString())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock size must be a positive number");
            }
            else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }

    }

    @GetMapping("/api/products/{id}")
    @ResponseBody
    public ProductEntity getProductById(@PathVariable String id) {
        try {
            return productService.getProductById(id);
        }
        catch (Exception e){
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }
            else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }

    @GetMapping("/api/products")
    @ResponseBody
    public List<ProductEntity> getAllProducts() {
        try {
            return productService.getAllProducts();
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
        }
    }

    @PutMapping("/api/products/{id}")
    @ResponseBody
    public void updateProductById(@PathVariable String id, @RequestBody ProductDTO updateProduct) {
        try {
            productService.updateProductById(id, updateProduct);
        }
        catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }
            else if (e.getMessage().equals(HttpStatus.BAD_REQUEST.toString())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock size must be a positive number");
            }
            else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }

    @DeleteMapping("/api/products/{id}")
    @ResponseBody
    public void deleteProductById(@PathVariable String id) {
        try {
            productService.deleteProductById(id);
        }
        catch (Exception e)
        {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }
            else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }
}
