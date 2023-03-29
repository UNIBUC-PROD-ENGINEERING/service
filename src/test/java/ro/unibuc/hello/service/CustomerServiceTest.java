package ro.unibuc.hello.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.CustomerRepository;
import ro.unibuc.hello.dto.Customer;

@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Test
    public void test_getCustomerByName_returnsCustomer() {
        // Arrange

        // Act

        // Assert

        }
}
