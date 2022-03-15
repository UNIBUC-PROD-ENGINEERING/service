package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreetingTest {

    Greeting greeting = new Greeting(12312312L,"Hello");

    @Test
    void getId() {
        assertEquals(12312312L,greeting.getId());
    }

    @Test
    void getContent() {
        assertSame("Hello", greeting.getContent());
    }
}