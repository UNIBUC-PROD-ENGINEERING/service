package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.hello.data.product.ProductEntity;
import ro.unibuc.hello.service.ProductService;


@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/api/products")
    @ResponseBody
    public ProductEntity postProduct(@RequestBody ProductEntity postProduct){
            return productService.insertProduct(postProduct);
    }

    @GetMapping("/api/products/{id}")
    @ResponseBody
    public ProductEntity getProductById(@PathVariable String id) throws Exception {
        try {
            return productService.getProductById(id);
        }
        catch(Exception e){
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
            }
            else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }
}
