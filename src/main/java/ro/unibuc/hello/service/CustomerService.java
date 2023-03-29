package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.CustomerEntity;
import ro.unibuc.hello.data.CustomerRepository;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.Optional;

@Component
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomerByName(String Name) throws EntityNotFoundException {
        CustomerEntity entity = customerRepository.findByName(Name);
        if(entity == null){
            throw new EntityNotFoundException(Name);
        }
        return new Customer(entity.name, entity.age);
    }
}
