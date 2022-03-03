package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.ProductRepository;
import ro.unibuc.hello.data.ProductEntity;
import ro.unibuc.hello.dto.ProductAddStockDto;
import ro.unibuc.hello.dto.ProductDto;
import ro.unibuc.hello.dto.AddProductDto;
import ro.unibuc.hello.dto.ProductSellStockDto;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/product")
    @ResponseBody
    public ProductDto sayHello(@RequestParam(name="name", required=false, defaultValue="Test") String name) {
        var entity = productRepository.findByTitle(name);
        if(entity != null) {
            return new ProductDto(entity);
        }
        return null;
    }

    @PostMapping("/products/addStock")
    public ResponseEntity<String> addProductStock(@RequestBody ProductAddStockDto model) {

        if (model.quantity <= 0) {
            return new ResponseEntity<>("negative quantity", HttpStatus.BAD_REQUEST);
        }
        var product = productRepository.findByTitle(model.title);
        if (product == null) {
            return new ResponseEntity<>("product not found", HttpStatus.NOT_FOUND);
        }

        product.quantity += model.quantity;
        productRepository.save(product);
        return new ResponseEntity<>("added", HttpStatus.OK);
    }

    @PostMapping("/products/sellStock")
    public ResponseEntity<String> sellProductStock(@RequestBody ProductSellStockDto model) {

        if (model.quantity <= 0) {
            return new ResponseEntity<>("negative quantity", HttpStatus.BAD_REQUEST);
        }
        var product = productRepository.findByTitle(model.title);
        if (product == null) {
            return new ResponseEntity<>("product not found", HttpStatus.NOT_FOUND);
        }

        product.quantity -= model.quantity;
        productRepository.save(product);
        return new ResponseEntity<>("sold", HttpStatus.OK);
    }

    @GetMapping("/products")
    @ResponseBody
    public List<ProductDto> getAllProducts() {
        List<ProductDto> products = new ArrayList<ProductDto>();
        var entities = productRepository.findAll();
        if(!entities.isEmpty()) {
            for(ProductEntity entity : entities)
            {
                products.add(new ProductDto(entity));
            }
            return products;
        }
        return new ArrayList<ProductDto>();
    }

    @PostMapping("/products/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody AddProductDto model) {

        if (model.quantity <= 0) {
            return new ResponseEntity<>("negative quantity", HttpStatus.BAD_REQUEST);
        }
        ProductEntity product = new ProductEntity(model.title, model.description, model.quantity);
        productRepository.save(product);
        return new ResponseEntity<>("added", HttpStatus.OK);
    }
}
