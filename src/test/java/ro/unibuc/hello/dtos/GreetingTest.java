package ro.unibuc.hello.dtos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GreetingTest {

    GreetingDTO myGreetingDTO = new GreetingDTO(1, "John");

    @Test
    void test_content(){
        Assertions.assertSame("John", myGreetingDTO.getContent());
    }
    @Test
    void test_id(){
        Assertions.assertEquals(1, myGreetingDTO.getId());
    }

}