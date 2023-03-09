package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private final AtomicLong counter = new AtomicLong();
    

}
