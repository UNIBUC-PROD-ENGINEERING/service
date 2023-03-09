package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import ro.unibuc.hello.data.product.ProductEntity;
import ro.unibuc.hello.data.product.ProductRepository;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductEntity insertProduct(ProductEntity product){
        return productRepository.save(product);
    }

    public ProductEntity getProductById(String id) throws Exception {
        return productRepository.findById(id).orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
    }
}
