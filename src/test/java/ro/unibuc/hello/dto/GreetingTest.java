package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GreetingTest {
    Greeting myGreeting = new Greeting(1, "Catalin");
    @Test
    void test_content(){
        Assertions.assertSame("Catalin", myGreeting.getContent());
    }
    @Test
    void test_id(){
        Assertions.assertEquals(1, myGreeting.getId());
    }

    @Test
    void test_remaining_functionalities() {
        long id = 1;
        String content = "test";
        Greeting greeting = new Greeting();

        Assertions.assertEquals(0, greeting.getId());
        Assertions.assertEquals(null, greeting.getContent());

        greeting.setId(id);
        greeting.setContent(content);

        Assertions.assertEquals(id, greeting.getId());
        Assertions.assertEquals(content, greeting.getContent());
    }

}