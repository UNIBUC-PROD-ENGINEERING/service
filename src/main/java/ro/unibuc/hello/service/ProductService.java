package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.ProductEntity;
import ro.unibuc.hello.data.ProductRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.dto.Product;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private final AtomicLong counter = new AtomicLong();

    
    public Product saveProduct(Product product)
    {
        ProductEntity productEntity = new ProductEntity(
        );
        
        productEntity.categories = product.getCategories();
        productEntity.description = product.getDescription();

        productRepository.save(productEntity);
        return product;
    }
    

}
