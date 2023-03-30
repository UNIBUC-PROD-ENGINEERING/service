package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class CustomerTest {
    Customer myCustomer = new Customer("John Doe", 25, "jdoe@gmail.com");

    @Test
    void test_name(){
        Assertions.assertSame("John Doe", myCustomer.getName());
    }

    @Test
    void test_age(){
        Assertions.assertEquals(25, myCustomer.getAge());
    }

    @Test
    void test_email(){
        Assertions.assertSame("jdoe@gmail.com", myCustomer.getEmail());
    }
}
