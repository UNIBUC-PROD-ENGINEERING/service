package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.CustomerEntity;
import ro.unibuc.hello.data.CustomerRepository;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public String getCustomers(String name){
        CustomerEntity entity = customerRepository.findByName(name);
        if(entity==null){
            throw new EntityNotFoundException(name);
        }
        return entity.getCustomers().toString();
    }

    public Customer getCustomer(String name, long customer_id){
        CustomerEntity entity = customerRepository.findByName(name);
        if (entity==null){
            throw new EntityNotFoundException(name);
        }
        return entity.getCustomer(customer_id);
    }

    public void delCustomer(long customer_id, String name){
        CustomerEntity entity = customerRepository.findByName(name);
        if (entity == null) {
            throw new EntityNotFoundException(name);
        }
        ArrayList<Customer> customernew = entity.delCustomer(customer_id);
        entity.setCustomers(customernew);
        customerRepository.deleteAll();
        customerRepository.save(entity);
    }

    public String addCustomers(String db, String name, int age, String email){
        Customer c = new Customer(name, age, email);
        CustomerEntity entity = customerRepository.findByName(db);
        if (entity == null) {
            throw new EntityNotFoundException(db);
        }
        entity.addCustomer(c);
        customerRepository.save(entity);
        return Arrays.toString(new ArrayList[]{entity.getCustomers()});
    }

}
