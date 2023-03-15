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

    public Customer getCustomerById(String Id) throws EntityNotFoundException {
        Optional<CustomerEntity> entity = customerRepository.findById(Id);
        if(entity == null){
            throw new EntityNotFoundException(Id);
        }
        return new Customer(entity.get().name, entity.get().age, entity.get().ticket);
    }
}
