package ro.unibuc.booking.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ro.unibuc.booking.dto.Greeting;

class GreetingTest {

    Greeting myGreeting = new Greeting("1", "John");

    @Test
    void test_content(){
        Assertions.assertSame("John", myGreeting.getContent());
    }
    @Test
    void test_id(){
        Assertions.assertEquals("1", myGreeting.getId());
    }

}
