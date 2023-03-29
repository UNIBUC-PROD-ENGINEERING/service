package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.CustomerRepository;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.dto.Greeting;

@SpringBootTest
@Tag("IT")
class CustomerServiceTestIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Test
    void test_findByName_returnAge() {
        // Arrange
        String name = "Andrei";
        Integer age = 20;

        // Act
        Customer customer = customerService.getCustomerByName(name);

        // Assert
        Assertions.assertEquals(age, customer.getAge());

    }

}
