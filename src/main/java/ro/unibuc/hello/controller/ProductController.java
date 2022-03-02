package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.data.ProductRepository;
import ro.unibuc.hello.dto.ProductDto;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/product")
    @ResponseBody
    public ProductDto sayHello(@RequestParam(name="name", required=false, defaultValue="Test") String name) {
        var entity = productRepository.findByTitle(name);
        if(entity != null)
            return new ProductDto(entity);
        return null;
    }
}
