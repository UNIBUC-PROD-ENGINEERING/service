package ro.unibuc.hello.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.data.CustomerEntity;
import ro.unibuc.hello.data.CustomerRepository;
import ro.unibuc.hello.dto.Customer;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository mockCustomerRepository;

    @InjectMocks
    CustomerService customerService = new CustomerService();

    @Test
    void test_findByName_returnsCustomer() {
        // Arrange
        String name = "Andrei";
        Integer age = 32;
        CustomerEntity customerEntity = new CustomerEntity(name, age);

        when(mockCustomerRepository.findByName(name)).thenReturn(customerEntity);

        // Act
        Customer customer = customerService.getCustomerByName(name);

        // Assert
        Assertions.assertEquals(age, customer.getAge());
    }
}
