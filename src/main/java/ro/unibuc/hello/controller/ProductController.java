package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.dto.Product;
import ro.unibuc.hello.exception.EntityNotFoundException;


@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    
    
}
