package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.ProductDTO;
import ro.unibuc.hello.service.ProductService;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/{name}")
    @ResponseBody
    public List<ProductDTO> getProductsByName(@PathVariable String name){
        return productService.findProductsByName(name);
    }
    @GetMapping("/product")
    @ResponseBody
    public List<ProductDTO> getAllProducts(){
        return productService.findAllProducts();
    }

    @PostMapping("/product")
    @ResponseBody
    public String uploadProduct(@RequestBody ProductDTO productDTO){
        if(productService.uploadProduct(productDTO)){
            return "Success";
        }
        return "Upload failed";

    }
}
